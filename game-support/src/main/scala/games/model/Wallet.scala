package games.model

trait Wallet {

  def wager(amount: Money)

  def payout(amount: Money)

  def getBalance: Money

}

object NullWallet extends Wallet {
  override val getBalance = Money(0)

  override def payout(amount: Money): Unit = {}

  override def wager(amount: Money): Unit = {}
}