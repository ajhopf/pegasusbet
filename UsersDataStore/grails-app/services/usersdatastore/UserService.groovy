package usersdatastore

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import users.Role
import users.User
import users.UserRole
import users.Wallet

@Transactional
class UserService {

    def createAdmin(String username, String password) {
        Wallet wallet = new Wallet(
                amount: 0
        )

        Role role =  Role.findByAuthority('ADMIN')

        User user = new User(
                username: username,
                password: password,
                wallet: wallet
        )

        user = user.save(flush: true)

        UserRole.create(user, role, true)
    }

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
