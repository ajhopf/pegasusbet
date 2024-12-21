package kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import serializers.HorseSerializer
import models.Horse

class HorseService {

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
            throw new RuntimeException("Failed to produce Horse to Kafka", e)
        }
    }
}
