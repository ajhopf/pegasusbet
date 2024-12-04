package kafka

import grails.gorm.transactions.Transactional
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import serializers.HorseSerializer
import webcrawler.Horse

@Transactional
class HorseService {

    Horse addHorse(Horse horse) {
        Horse existingHorse = Horse.findByNameAndAge(horse.name, horse.age)

        if (!existingHorse) {
            Horse newHorse = horse.save()
            return newHorse
        } else {
            existingHorse.numberOfRaces = horse.numberOfRaces
            existingHorse.lastResults = horse.lastResults
            existingHorse.numberOfVictories = horse.numberOfVictories
            existingHorse.save()

            return existingHorse
        }


    }

    void produceHorse(Horse horse) {
        Properties properties = new Properties()

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 'localhost:9092')
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName())
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, HorseSerializer.class.getName())

        try (KafkaProducer<String, Horse> producer = new KafkaProducer<>(properties)) {

            ProducerRecord<String, Horse> record =
                    new ProducerRecord<>("horses", horse)
            producer.send(record)

        } catch (Exception e) {
            throw new RuntimeException("Failed to produce RaceCourse to Kafka", e)
        }
    }
}
