package gf.model.core

trait Wallet {

  def wager(amount: BigDecimal)

  def payout(amount: BigDecimal)

  def getBalance: BigDecimal

}

object NullWallet extends Wallet {
  override val getBalance = BigDecimal(0)

  override def payout(amount: BigDecimal): Unit = {}

  override def wager(amount: BigDecimal): Unit = {}
}