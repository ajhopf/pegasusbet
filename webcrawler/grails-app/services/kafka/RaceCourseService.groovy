package kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import serializers.RaceCourseSerializer
import models.RaceCourse

class RaceCourseService {

    synchronized RaceCourse addRaceCourse(String name) {
        RaceCourse existingRaceCourse = RaceCourse.findByName(name, [lock: true])

        if (!existingRaceCourse) {
            RaceCourse newRaceCourse = new RaceCourse(name: name)
            newRaceCourse.save(flush: true)
            return newRaceCourse
        }

        return existingRaceCourse
    }

    void produceRaceCourse(String raceCourseName) {
        Properties properties = new Properties()

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 'localhost:9092')
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName())
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, RaceCourseSerializer.class.getName())

        RaceCourse raceCourse = new RaceCourse(name: raceCourseName)

        try (KafkaProducer<String, RaceCourse> producer = new KafkaProducer<>(properties)) {

            ProducerRecord<String, RaceCourse> record =
                    new ProducerRecord<>("race-courses", raceCourse)
            producer.send(record)

        } catch (Exception e) {
            throw new RuntimeException("Failed to produce RaceCourse to Kafka", e)
        }
    }
}
