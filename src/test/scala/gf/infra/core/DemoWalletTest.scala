package gf.infra.core

import gf.model.core.Money
import org.junit.Assert.assertEquals
import org.junit.Test

class DemoWalletTest {
  private val wallet = new DemoWallet

  @Test
  def payout() = {
    wallet.payout(Money(10))
    assertEquals(Money(1010), wallet.getBalance)
  }

  @Test
  def wagerLoops() = {
    wallet.wager(Money(1010))
    assertEquals(Money(1000), wallet.getBalance)
  }

  @Test
  def startingBalance() = assertEquals(Money(1000), wallet.getBalance)

  @Test
  def wager() = {
    wallet.wager(Money(10))
    assertEquals(Money(990), wallet.getBalance)
  }

}
