package gf.roulette

import gf.core.Wallet


sealed case class Pocket(number: Int) {
  require(number >= 0)
  require(number <= 36)
}

sealed trait Bet {
  val amount: BigDecimal
}

case class NumberBet(amount: BigDecimal, pocket: Pocket) extends Bet {
  require(pocket.number > 0)
}

case class Roulette(random: () => Pocket, wallet: Wallet, var pocket: Pocket = Pocket(0), var bets: List[Bet] = List()) {
  def addBet(bet: Bet): Unit = {
    wallet.wager(bet.amount)
    bets = bet :: bets
  }

  def spin(): Unit = {
    pocket = random()
    val payouts = bets.map {
      case NumberBet(amount, pocket1) => if (pocket1 == pocket) Some(amount * 10) else None
      case _ => None
    }
    bets = List()
    payouts.foreach { case Some(payout) => wallet.payout(payout) }
  }
}
