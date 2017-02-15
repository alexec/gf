package gf

trait Wallet {

  def wager(amount: BigDecimal)

  def getBalance: BigDecimal

}

object NullWallet extends Wallet {
  override def wager(amount: BigDecimal) = {}

  override val getBalance = BigDecimal(0)
}