package usersdatastore

import grails.gorm.transactions.Transactional
import users.*


class BootStrap {
    UserService userService

    def init = { servletContext ->
        createAdminUser()
    }

    @Transactional
    void createAdminUser() {
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
