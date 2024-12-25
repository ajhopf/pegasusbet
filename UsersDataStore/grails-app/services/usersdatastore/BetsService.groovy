package usersdatastore

import dtos.BetResponseDTO
import dtos.CreateBetDTO
import exceptions.InsuficientFundsException
import grails.gorm.transactions.Transactional
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import serializers.BetSerializer
import users.Bet
import users.BetStatus
import users.User
import users.Wallet

import java.time.LocalDateTime

@Transactional
class BetsService {

    BetResponseDTO createBet(User user, CreateBetDTO createBetDTO) {
        LocalDateTime now = LocalDateTime.now()

        Wallet userWallet = Wallet.findByUser(user)

        if (userWallet.amount < createBetDTO.amount) {
            throw new InsuficientFundsException('Usuário não possui recursos suficientes')
        }

        Bet bet = new Bet(
                user: user,
                amount: createBetDTO.amount,
                raceHorseJockeyId: createBetDTO.raceHorseJockeyId,
                timeStamp: now,
                status: BetStatus.WAITING
        )

        bet = bet.save(flush: true)

        BetResponseDTO betResponseDTO =  new BetResponseDTO(bet)
        produceBet(betResponseDTO)

        return betResponseDTO
    }

    List<BetResponseDTO> getUsersBets(User user) {
        List<Bet> bets = Bet.findAllByUser(user)

        return bets.collect(bet -> new BetResponseDTO(bet))
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
