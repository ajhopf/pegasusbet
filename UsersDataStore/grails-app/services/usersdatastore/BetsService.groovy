package usersdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import dtos.BetResponseDTO
import dtos.CreateBetDTO
import dtos.RaceResultPositions
import dtos.TransactionDTO
import dtos.WalletDTO
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

        TransactionDTO transactionDTO = new TransactionDTO(
                amount: createBetDTO.amount,
                transactionType: TransactionType.PLACE_BET
        )

        walletService.addTransaction(user, transactionDTO)

        bet = bet.save(flush: true)

        BetResponseDTO betResponseDTO =  new BetResponseDTO(bet)
        produceBet(betResponseDTO)

        return betResponseDTO
    }


    List<BetResponseDTO> getUsersBets(User user) {
        List<Bet> bets = Bet.findAllByUser(user)

        return bets.collect(bet -> new BetResponseDTO(bet))
    }

    @Transactional
    void processRaceResult(String raceResultString) {
        ObjectMapper objectMapper = new ObjectMapper()
        Map<String, Object> results = objectMapper.readValue(raceResultString, Map.class)

        List<RaceResultPositions> positions = results.positions as RaceResultPositions[]
        Bet bet = Bet.get(13729)

        bet.status = BetStatus.LOSS
        bet.save(flush: true)


//        positions.each { RaceResultPositions raceResultPositions ->
//            Bet bet = Bet.get(13729)
//
//            processBet(raceResultPositions, bet)
//
////            List<Bet> bets = findBets(raceResultPositions.raceHorseJockeyId)
////
////
////            bets.each { Bet bet ->
////                processBet(raceResultPositions, bet)
////            }
//        }
    }

    List<Bet> findBets(Long raceHorseJockeyId) {
        List<Bet> bets

        bets = Bet.findAllByRaceHorseJockeyId(raceHorseJockeyId)

        return bets
    }

    void processBet(RaceResultPositions raceResultPositions, Bet bet) {
        Integer raceHorseResult = raceResultPositions.result.split("/")[0].toInteger()

        switch (bet.betType) {
            case 'WIN':
                raceHorseResult == 1 ? processBetWin(bet, raceResultPositions.odds) : processBetLoss(bet)
                break
            case 'PLACE':
                raceHorseResult < 3 ? processBetWin(bet, raceResultPositions.odds) : processBetLoss(bet)
                break
            default:
                raceHorseResult < 4 ? processBetWin(bet, raceResultPositions.odds) : processBetLoss(bet)
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

//        walletService.addTransaction(bet.user, transactionDTO)
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
