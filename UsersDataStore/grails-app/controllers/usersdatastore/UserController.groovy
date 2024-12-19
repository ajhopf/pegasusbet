package usersdatastore

import dtos.CreateUserDTO
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class UserController {
    UserService userService

	static responseFormats = ['json']

    def save(CreateUserDTO createUserDTO) {
        if (!createUserDTO.username || !createUserDTO.password) {
            render status: 400, text: "Username and password are required"
            return
        }

        try {
            userService.createUser(createUserDTO.username, createUserDTO.password)

            render(status: 201, contentType: 'application/json') {

            }
        } catch (Exception e) {
            println e.getMessage()

            render(status: 400,  contentType: 'application/json') {
                error e.getMessage()
            }
        }
    }
}
