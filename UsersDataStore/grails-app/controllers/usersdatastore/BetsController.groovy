package usersdatastore

import dtos.bet.BetResponseDTO
import dtos.bet.CreateBetDTO
import exceptions.InsuficientFundsException
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import users.User

@Secured(['isAuthenticated()'])
class BetsController {
    BetsService betsService

	static responseFormats = ['json', 'xml']
	
    def createBet(CreateBetDTO createBetDTO) {
        'print ln in create bet controller'
        try {
            String username = SecurityContextHolder.context.authentication.name
            User currentUser = User.findByUsername(username)

            if (!currentUser) {
                render(status: 401, contentType: "application/json") {
                    message "Unauthorized"
                }
                return
            }

            BetResponseDTO betResponseDTO = betsService.createBet(currentUser, createBetDTO)

            render(status: 201, contentType: "application/json") {
                bet betResponseDTO
            }
        } catch (InsuficientFundsException e) {
            render(status: 400, contentType: "application/json") {
                message e.getMessage()
            }
        } catch (Exception e) {
            render(status: 500, contentType: "application/json") {
                message "Não foi possivel completar a chamada"
                cause e.getCause()
            }
        }


    }

    def getUserBets() {
        try {
            String username = SecurityContextHolder.context.authentication.name
            User currentUser = User.findByUsername(username)

            if (!currentUser) {
                render(status: 401, contentType: "application/json") {
                    message "Unauthorized"
                }
                return
            }

            List<BetResponseDTO> betResponseDTOList = betsService.getUsersBets(currentUser)

            render(status: 200, contentType: "application/json") {
                bets betResponseDTOList
            }
        } catch (Exception e) {
            render(status: 500, contentType: "application/json") {
                message "Não foi possivel completar a chamada"
                cause e.getCause()
            }
        }
    }
}
