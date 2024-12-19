package dtos

import users.Wallet

class WalletDTO {
    String id
    BigDecimal amount

    WalletDTO(Wallet wallet) {
        this.id = wallet.id
        this.amount = wallet.amount
    }
}
