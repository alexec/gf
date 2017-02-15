package gf.core

trait Wallet {

  def wager(amount: BigDecimal)

  def getBalance: BigDecimal

}

object NullWallet extends Wallet {
  override val getBalance = BigDecimal(0)

  override def wager(amount: BigDecimal) = {}
}