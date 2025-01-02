import dao.BetDAO
import dao.WalletDAO
import users.UserPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    walletDAO(WalletDAO)
    betDAO(BetDAO)
}
