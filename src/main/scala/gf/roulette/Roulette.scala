package gf.roulette

import gf.core.Wallet


sealed case class Pocket(number: Int) {
  require(number >= 0)
  require(number <= 36)
}

sealed trait Bet {
  val amount: BigDecimal

  def payout(pocket: Pocket) = amount * payoutMultiplier(pocket)

  def payoutMultiplier(pocket: Pocket): Int
}

case class NumberBet(amount: BigDecimal, pocket: Pocket) extends Bet {
  require(pocket.number > 0)

  override def payoutMultiplier(pocket: Pocket): Int = if (pocket == this.pocket) 35 else 0
}

case class Roulette(random: () => Pocket, wallet: Wallet, var pocket: Pocket = Pocket(0), var bets: List[Bet] = List()) {
  def addBet(bet: Bet): Unit = {
    wallet.wager(bet.amount)
    bets = bet :: bets
  }

  def spin(): Unit = {
    pocket = random()
    val payouts = bets.map {
      _.payout(pocket)
    }
    bets = List()
    payouts.foreach {
      wallet.payout
    }
  }
}
