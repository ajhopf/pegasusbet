package usersdatastore

import users.*

class BootStrap {

    def init = { servletContext ->
//        def roleAdmin = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
//        def adminUser = User.findOrSaveWhere(username: 'admin', password: 'admin123', enabled: true)
//
//        if (!UserRole.exists(adminUser.id, roleAdmin.id)) {
//            UserRole.create(adminUser, roleAdmin, true)
//        }
    }
    def destroy = {}
}
