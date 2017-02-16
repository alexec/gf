package gf.roulette

import gf.core.SimpleWallet
import org.junit.Assert.assertEquals
import org.junit.Test

class RouletteTest {
  private val wallet = SimpleWallet()
  private val winningPocket = Pocket(1)
  private val losingPocket = Pocket(20)
  private val random = () => winningPocket
  private val roulette = Roulette(random = random, wallet = wallet)
  private val wager = BigDecimal(10)
  private val winningBet = NumberBet(wager, winningPocket)
  private val losingBet = NumberBet(wager, losingPocket)

  @Test def pocketCanBeZero(): Unit = Pocket(0)


  @Test def pocketCanBe36(): Unit = Pocket(36)

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBeNegative(): Unit =
    Pocket(-1)

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBe37(): Unit =
    Pocket(37)


  @Test(expected = classOf[IllegalArgumentException]) def cannotBetOnZero() = {
    roulette.addBet(NumberBet(wager, Pocket(0)))
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotBetOn37() = {
    roulette.addBet(NumberBet(wager, Pocket(37)))
  }

  @Test def addingBetDecreasesBalance() = {
    roulette.addBet(winningBet)
    assertEquals(BigDecimal(990), wallet.getBalance)
  }

  @Test def addedBetIsAdded() = {
    val bet = NumberBet(wager, Pocket(1))
    roulette.addBet(bet)
    assertEquals(List(bet), roulette.bets)
  }

  @Test def spinClearsBets() = {
    roulette.addBet(winningBet)
    roulette.spin()
    assertEquals(List.empty, roulette.bets)
  }

  @Test def spinEmptiesBet() = {
    roulette.addBet(winningBet)
    roulette.spin()
    assertEquals(List.empty, roulette.bets)
  }

  @Test def losingBetDoesNotPayout() = {
    roulette.addBet(losingBet)
    roulette.spin()
    assertEquals(BigDecimal(990), wallet.getBalance)
  }

  @Test def winningNumberBetPays35x() = {
    roulette.addBet(winningBet)
    roulette.spin()
    assertEquals(BigDecimal(990 + 350), wallet.getBalance)
  }
}
