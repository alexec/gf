package gf.roulette

import gf.core.SimpleWallet
import org.junit.Assert.assertEquals
import org.junit.Test

class RouletteTest {
  private val wallet = SimpleWallet()
  private val roulette = Roulette(random = () => 1, wallet = wallet)

  @Test def addedBetIsAdded() = {
    val bet = NumberBet(BigDecimal(10), 1)
    roulette.addBet(bet)
    assertEquals(List(bet), roulette.bets)
  }
}
