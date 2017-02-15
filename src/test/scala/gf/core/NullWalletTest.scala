package gf.core

import org.junit.Assert.assertEquals
import org.junit.Test

class NullWalletTest {

  @Test
  def payout() = {
    NullWallet.payout(BigDecimal(10))
    balanceIsZero()
  }

  @Test
  def wager() = {
    NullWallet.wager(BigDecimal(10))
    balanceIsZero()
  }

  @Test
  def balanceIsZero() = assertEquals(BigDecimal(0), NullWallet.getBalance)
}
