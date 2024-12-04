package pegasusdatastore

import grails.testing.services.ServiceUnitTest
import kafka.KafkaConsumerService
import spock.lang.Specification

class KafkaConsumerServiceSpec extends Specification implements ServiceUnitTest<KafkaConsumerService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
