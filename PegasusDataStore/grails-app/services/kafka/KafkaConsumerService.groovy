package kafka


import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.scheduling.annotation.Async
import pegasusdatastore.HorseService
import pegasusdatastore.Jockey
import pegasusdatastore.JockeyService
import pegasusdatastore.RaceCourseService

import java.time.Duration

class KafkaConsumerService {

    HorseService horseService
    RaceCourseService raceCourseService
    JockeyService jockeyService

    @Async
    void startConsumer() {
        Properties properties = new Properties()

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 'localhost:9092')
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, 'grails-consumer-group')
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, 'earliest')
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, 'true')

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)

        // Assinar múltiplos tópicos
        consumer.subscribe(['jockeys', 'horses', 'race-courses'])

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1))

                records.each { ConsumerRecord<String, String> record ->
                    println "Received message: ${record.value()} from topic: ${record.topic()}"

                    switch (record.topic()) {
                        case 'jockeys':
                            processJockey(record)
                            break
                        case 'horses':
                            processHorse(record)
                            break
                        case 'race-courses':
                            processRacecourse(record)
                            break
                    }
                }
            }
        } catch (Exception e) {
            println "Error consuming messages: ${e.message}"
        } finally {
            consumer.close()
        }
    }

    private void processJockey(ConsumerRecord<String, String> record) {
        jockeyService.addJockey(record.value())
    }

    private void processHorse(ConsumerRecord<String, String> record) {
        horseService.addHorse(record.value())
    }

    private void processRacecourse(ConsumerRecord<String, String> record) {
        raceCourseService.addRaceCourse(record.value())
    }
}
