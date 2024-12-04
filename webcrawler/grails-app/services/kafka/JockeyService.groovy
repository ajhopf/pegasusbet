package kafka

import grails.gorm.transactions.Transactional
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import serializers.JockeySerializer
import serializers.RaceCourseSerializer
import webcrawler.Jockey
import webcrawler.RaceCourse

@Transactional
class JockeyService {

    Jockey addJockey(Jockey jockey) {
        Jockey existingJockey = Jockey.findByName(jockey.name, [lock: true])

        if (!existingJockey) {
            Jockey newJockey = jockey.save(flush: true)

            return newJockey
        } else {
            existingJockey.numberOfRaces = jockey.numberOfRaces
            existingJockey.lastResults = jockey.lastResults
            existingJockey.numberOfVictories = jockey.numberOfVictories

            existingJockey.save()

            return existingJockey
        }
    }

    void produceJockey(Jockey jockey) {
        Properties properties = new Properties()

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 'localhost:9092')
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName())
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JockeySerializer.class.getName())

        try (KafkaProducer<String, Jockey> producer = new KafkaProducer<>(properties)) {

            ProducerRecord<String, Jockey> record =
                    new ProducerRecord<>("jockeys", jockey)
            producer.send(record)

        } catch (Exception e) {
            throw new RuntimeException("Failed to produce Jockey to Kafka", e)
        }
    }

}
