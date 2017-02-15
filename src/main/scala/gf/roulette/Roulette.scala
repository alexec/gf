package gf.roulette

import gf.core.Wallet


trait Bet {
  val amount: BigDecimal
}

case class NumberBet(amount: BigDecimal, pocket: Pocket) extends Bet

case class Roulette(random: () => Pocket, wallet: Wallet, var pocket: Pocket = 0, var bets: List[Bet] = List()) {
  def addBet(bet: Bet) = {
    wallet.wager(bet.amount)
    bets = bet :: bets
  }

  def spin() = {
    pocket = random()
    val payouts = bets.map {
      case NumberBet(amount, pocket1) => if (pocket1 == pocket) Some(amount * 10) else None
      case _ => None
    }
    bets = List()
    payouts.foreach { case Some(payout) => wallet.payout(payout) }
  }
}
