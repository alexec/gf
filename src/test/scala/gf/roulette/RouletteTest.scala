package gf.roulette

import gf.core.SimpleWallet
import org.junit.Assert.assertEquals
import org.junit.Test

class RouletteTest {
  private val wallet = SimpleWallet()
  private val roulette = Roulette(random = () => Pocket(1), wallet = wallet)

  @Test def pocketCanBeZero(): Unit = Pocket(0)


  @Test def pocketCanBe36(): Unit = Pocket(36)

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBeNegative(): Unit =
    Pocket(-1)

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBe37(): Unit =
    Pocket(37)


  @Test(expected = classOf[IllegalArgumentException]) def cannotBetOnZero() = {
    roulette.addBet(NumberBet(BigDecimal(1), Pocket(0)))
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotBetOn37() = {
    roulette.addBet(NumberBet(BigDecimal(1), Pocket(37)))
  }

  @Test def addedBetIsAdded() = {
    val bet = NumberBet(BigDecimal(10), Pocket(1))
    roulette.addBet(bet)
    assertEquals(List(bet), roulette.bets)
  }
}
