package pegasusdatastore

import kafka.KafkaConsumerService

class BootStrap {

    KafkaConsumerService kafkaConsumerService

    def init = { servletContext ->
        kafkaConsumerService.startConsumer()
    }

    def destroy = {
    }
}
