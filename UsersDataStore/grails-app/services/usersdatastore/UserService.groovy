package usersdatastore

import grails.gorm.transactions.Transactional
import users.Role
import users.User
import users.UserRole
import users.Wallet

@Transactional
class UserService {
    def createAdmin(String username, String password) {
        createGenericUser(username, password, 'ROLE_ADMIN')
    }

    def createGenericUser(String username, String password) {
        createGenericUser(username, password, 'ROLE_USER')
    }

    def createGenericUser(String username, String password, String userRoleString) {
        Wallet wallet = new Wallet(
                amount: 0
        )

        Role role = Role.findOrSaveWhere(authority:  userRoleString)

        User user = new User(
                username: username,
                password: password,
                wallet: wallet,
                role: role
        )

        user = user.save(flush: true)

        UserRole userRole = new UserRole(user: user, role: role)
        userRole.save(flush: true)
    }

}
