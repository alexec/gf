package core.model

import org.junit.Assert.assertEquals
import org.junit.Test

class NullWalletTest {

  @Test
  def payout() = {
    NullWallet.payout(Money(10))
    balanceIsZero()
  }

  @Test
  def balanceIsZero() = assertEquals(Money(0), NullWallet.getBalance)

  @Test
  def wager() = {
    NullWallet.wager(Money(10))
    balanceIsZero()
  }

}
