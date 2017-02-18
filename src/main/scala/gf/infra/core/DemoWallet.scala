package gf.infra.core

import gf.infra.core.DemoWallet.defaultBalance
import gf.model.core.{Money, Wallet}

object DemoWallet {
  private val defaultBalance = Money(1000)
}

case class DemoWallet(var balance: Money = defaultBalance) extends Wallet {
  override def wager(amount: Money): Unit = {
    balance = balance - amount
    if (balance < 0) balance = defaultBalance
  }

  override def payout(amount: Money): Unit = balance = balance + amount

  override def getBalance: Money = balance
}
