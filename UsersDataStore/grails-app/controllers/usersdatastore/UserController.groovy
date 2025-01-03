package usersdatastore

import dtos.user.CreateUserDTO
import grails.plugin.springsecurity.annotation.Secured

class UserController {
    UserService userService

	static responseFormats = ['json']

    @Secured('permitAll')
    def save(CreateUserDTO createUserDTO) {
        if (!createUserDTO.username || !createUserDTO.password) {
            render status: 400, text: "Username and password are required"
            return
        }

        try {
            userService.createGenericUser(createUserDTO.username, createUserDTO.password)

            render(status: 201, contentType: 'application/json') {

            }
        } catch (Exception e) {
            println e.getMessage()

            render(status: 400,  contentType: 'application/json') {
                error e.getMessage()
            }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def createAdmin(CreateUserDTO createUserDTO) {
        if (!createUserDTO.username || !createUserDTO.password) {
            render status: 400, text: "Username and password are required"
            return
        }

        try {
            userService.createAdmin(createUserDTO.username, createUserDTO.password)

            render(status: 201, contentType: 'application/json') {

            }
        } catch (Exception e) {
            println e.getStackTrace()t
            println e.getMessage()

            render(status: 400,  contentType: 'application/json') {
                error e.getMessage()
            }
        }
    }


}
