package gf.model.core

case class SimpleWallet(var balance: BigDecimal = BigDecimal(1000)) extends Wallet {
  override def wager(amount: BigDecimal): Unit = balance = balance - amount

  override def payout(amount: BigDecimal): Unit = balance = balance + amount

  override def getBalance: BigDecimal = balance
}
