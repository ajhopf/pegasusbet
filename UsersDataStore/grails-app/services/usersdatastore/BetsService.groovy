package usersdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import dtos.bet.BetResponseDTO
import dtos.bet.CreateBetDTO
import dtos.TransactionDTO
import dtos.raceresult.HorseJockeyResult
import dtos.raceresult.RaceResultDTO
import enums.BetType
import enums.TransactionType
import exceptions.InsuficientFundsException
import grails.gorm.transactions.Transactional
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import serializers.BetSerializer
import users.Bet
import enums.BetStatus
import users.User
import users.Wallet

import java.time.LocalDateTime


class BetsService {

    WalletService walletService

    @Transactional
    BetResponseDTO createBet(User user, CreateBetDTO createBetDTO) {
        LocalDateTime now = LocalDateTime.now()

        Wallet userWallet = Wallet.findByUser(user)

        if (userWallet.amount < createBetDTO.amount) {
            throw new InsuficientFundsException('Usuário não possui recursos suficientes')
        }

        Bet bet = new Bet(
                user: user,
                amount: createBetDTO.amount,
                betType: createBetDTO.betType,
                raceHorseJockeyId: createBetDTO.raceHorseJockeyId,
                timeStamp: now,
                status: BetStatus.WAITING
        )

        bet = bet.save(flush: true)

        TransactionDTO transactionDTO = new TransactionDTO(
                amount: createBetDTO.amount,
                transactionType: TransactionType.PLACE_BET
        )

        walletService.addTransaction(user, transactionDTO)

        BetResponseDTO betResponseDTO =  new BetResponseDTO(bet)
        produceBet(betResponseDTO)

        return betResponseDTO
    }


    List<BetResponseDTO> getUsersBets(User user) {
        List<Bet> bets = Bet.findAllByUser(user)

        return bets.collect(bet -> new BetResponseDTO(bet))
    }

    @Transactional
    void processRaceResult(String raceHorseJockeyResultString) {
        ObjectMapper objectMapper = new ObjectMapper()
        Map<String, Object> results = objectMapper.readValue(raceHorseJockeyResultString, Map.class)
//
//        RaceResultDTO raceResultDTO = results as RaceResultDTO

        List<HorseJockeyResult> raceHorseJockeyPositions = ((List<Map>) results.positions).collect { Map positionData ->
            new HorseJockeyResult(
                    raceHorseJockeyId: positionData.raceHorseJockeyId as Long,
                    position: positionData.position as Double,
                    result: positionData.result as String,
                    odds: positionData.odds as Double
            )
        }

        raceHorseJockeyPositions.each { HorseJockeyResult horseJockeyResult ->
            Integer endPosition = horseJockeyResult.result.split("/")[0].toInteger()

            List<Bet> bets = Bet.findAllByRaceHorseJockeyId(horseJockeyResult.raceHorseJockeyId)

            bets.each { Bet bet ->
                BetStatus newStatus = determineBetStatus(bet, endPosition)

                if (newStatus == BetStatus.LOSS) {
                    processBetLoss(bet)
                } else {
                    processBetWin(bet, horseJockeyResult.odds)
                }
            }
        }
    }

    private BetStatus determineBetStatus(Bet bet, int resultPosition) {
        BetType betType = bet.betType

        if (betType == BetType.WIN && resultPosition == 1) {
            return BetStatus.WIN
        } else if (betType == BetType.PLACE && (resultPosition == 1 || resultPosition == 2)) {
            return BetStatus.WIN
        } else if (betType == BetType.SHOW && (resultPosition == 1 || resultPosition == 2 || resultPosition == 3)) {
            return BetStatus.WIN
        } else {
            return BetStatus.LOSS
        }
    }

    void processBetLoss(Bet bet) {
        bet.status = BetStatus.LOSS
        bet.save(flush: true, failOnError: true)
    }

    void processBetWin(Bet bet, Double multiplier) {
        bet.status = BetStatus.WIN
        bet.save(flush: true, failOnError: true)

        BigDecimal totalReturn = bet.amount * multiplier
        BigDecimal totalEarning = totalReturn - bet.amount
        BigDecimal totalAmountToDeposit

        switch (bet.betType) {
            case BetType.WIN:
                totalAmountToDeposit = totalReturn
                break
            case BetType.PLACE:
                totalAmountToDeposit = totalReturn - ( totalEarning * 0.3 )
                break
            default:
                totalAmountToDeposit = totalReturn - ( totalEarning * 0.5 )
        }

        TransactionDTO transactionDTO = new TransactionDTO(
                amount: totalAmountToDeposit,
                transactionType: TransactionType.BET_WIN
        )

        walletService.addTransaction(bet.user, transactionDTO)
    }

    void produceBet(BetResponseDTO betResponseDTO) {
        Properties properties = new Properties()

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 'localhost:9092')
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName())
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BetSerializer.class.getName())

        try (KafkaProducer<String, BetResponseDTO> producer = new KafkaProducer<>(properties)) {

            ProducerRecord<String, BetResponseDTO> record = new ProducerRecord<>("bets", betResponseDTO)
            producer.send(record)

        } catch (Exception e) {
            throw new RuntimeException("Failed to produce Horse to Kafka", e)
        }
    }
}
