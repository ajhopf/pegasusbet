package usersdatastore

import grails.gorm.transactions.Transactional
import users.User
import users.Wallet

@Transactional
class UserService {

    def createUser(String username, String password) {
        Wallet wallet = new Wallet(
                amount: 0
        )

        User user = new User(
                username: username,
                password: password,
                wallet: wallet
        )

        user.save(flush: true)
    }
}
