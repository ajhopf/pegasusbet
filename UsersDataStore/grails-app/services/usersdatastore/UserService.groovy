package usersdatastore

import grails.gorm.transactions.Transactional
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

        Role role = Role.findByAuthority('ROLE_ADMIN')

        User user = new User(
                username: username,
                password: password,
                wallet: wallet,
                role: role
        )

        user = user.save(flush: true)

        UserRole.create(user, role, true)
    }

    def createUser(String username, String password) {
        Wallet wallet = new Wallet(
                amount: 0
        )

        Role role =  Role.findByAuthority('ROLE_USER')

        User user = new User(
                username: username,
                password: password,
                wallet: wallet,
                role: role
        )

        user = user.save(flush: true)

        UserRole.create(user, role, true)
    }
}
