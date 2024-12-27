package usersdatastore

import grails.gorm.transactions.Transactional
import kafka.KafkaConsumerService
import users.*


class BootStrap {
    UserService userService
    KafkaConsumerService kafkaConsumerService

    def init = { servletContext ->
        createUsers()
        kafkaConsumerService.startConsumer()
    }

    @Transactional
    void createUsers() {
        User adminUser = User.findByUsername('admin')

        if (!adminUser) {
            userService.createAdmin('admin' , 'admin')
        }

        User genericUser = User.findByUsername('user')

        if (!genericUser) {
            userService.createGenericUser('user' , 'user')
        }
    }


    def destroy = {}
}
