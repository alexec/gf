package gf.model.core

case class SimpleWallet(var balance: Money = Money(1000)) extends Wallet {
  override def wager(amount: Money): Unit = balance = balance - amount

  override def payout(amount: Money): Unit = balance = balance + amount

  override def getBalance: Money = balance
}
