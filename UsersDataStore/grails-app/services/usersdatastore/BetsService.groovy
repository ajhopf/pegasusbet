package usersdatastore

import dtos.BetResponseDTO
import dtos.CreateBetDTO
import exceptions.InsuficientFundsException
import grails.gorm.transactions.Transactional
import users.Bet
import users.BetStatus
import users.User
import users.Wallet

import java.time.LocalDateTime

@Transactional
class BetsService {

    BetResponseDTO createBet(User user, CreateBetDTO createBetDTO) {
        LocalDateTime now = LocalDateTime.now()

        Wallet userWallet = Wallet.findByUser(user)

        if (userWallet.amount < createBetDTO.amount) {
            throw new InsuficientFundsException('Usuário não possui recursos suficientes')
        }

        Bet bet = new Bet(
                user: user,
                amount: createBetDTO.amount,
                raceHorseJockeyId: createBetDTO.raceHorseJockeyId,
                timeStamp: now,
                status: BetStatus.WAITING
        )

        bet = bet.save(flush: true)

        return new BetResponseDTO(bet)
    }

    List<BetResponseDTO> getUsersBets(User user) {
        List<Bet> bets = Bet.findAllByUser(user)

        return bets.collect(bet -> new BetResponseDTO(bet))
    }
}
