package gf.model.core

import java.text.NumberFormat

import scala.math.BigDecimal.RoundingMode

private case class StatyWallet(var totalWager: Money = Money(0), var totalPayout: Money = Money(0), balance: Money = Money(0)) extends Wallet {

  override def wager(amount: Money): Unit = totalWager = totalWager + amount

  override def payout(amount: Money): Unit = totalPayout = totalPayout + amount

  def returnToPlayer(): BigDecimal = totalPayout / totalWager

  override def getBalance: Money = balance
}


class MonteCarloSimulator[G](
                              factory: (Wallet => G),
                              play: (G => G),
                              expectedReturnToPlayer: BigDecimal,
                              expectedRange: BigDecimal
                            ) {
  private val wallet = new StatyWallet

  def main(args: Array[String]): Unit = {
    val totalPlays = 10000000
    val start = System.currentTimeMillis()
    println(s"Total plays: ${format(totalPlays)}")
    var game = factory(wallet)
    for (_ <- 0 to totalPlays) {
      game = play(game)
    }
    val durationSeconds = (System.currentTimeMillis() - start) / 1000
    println(s"Return to player: ${formatPercentage(wallet.returnToPlayer())} (expected ${formatPercentage(expectedReturnToPlayer)})")
    println(s"Plays per second: ${format(totalPlays / durationSeconds)}")
    println(s"Duration: ${format(durationSeconds)} second(s)")
    if (outOfExpectedRange()) println(s"Out of expected range")
  }

  private def formatPercentage(value: BigDecimal) = s"${(value * 100).setScale(2, RoundingMode.FLOOR)}%"

  private def format(value: Long): String = NumberFormat.getIntegerInstance.format(value)

  def outOfExpectedRange(): Boolean = (wallet.returnToPlayer() - expectedReturnToPlayer).abs > expectedRange
}
