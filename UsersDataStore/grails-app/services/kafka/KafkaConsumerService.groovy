package kafka

import enums.TransactionType
import grails.gorm.transactions.Transactional
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.scheduling.annotation.Async
import usersdatastore.BetsService

import java.time.Duration

class KafkaConsumerService {
    BetsService betsService

    @Async
    void startConsumer() {
        Properties properties = new Properties()

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 'localhost:9092')
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, 'users-consumer-group')
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, 'earliest')
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, 'true')

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)

        // Assinar múltiplos tópicos
        consumer.subscribe(['race-results'])

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1))

                records.each { ConsumerRecord<String, String> record ->
                    println "Received message: ${record.value()} from topic: ${record.topic()}"

                    switch (record.topic()) {
                        case 'race-results':
                            processRaceResult(record)
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

    @Transactional
    private void processRaceResult(ConsumerRecord<String, String> record) {
        println record.value()
        betsService.processRaceResult(record.value())
    }
}
