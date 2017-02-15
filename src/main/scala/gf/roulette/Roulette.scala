package gf.roulette

import gf.core.{Game, Wallet}


trait Bet {
  val amount: BigDecimal
}

case class NumberBet(amount: BigDecimal, pocket: Pocket) extends Bet

trait Request

case class AddBet(bet: Bet) extends Request

object Spin extends Request

case class Roulette(random: () => Pocket, pocket: Pocket, bets: List[Bet] = List()) extends Game[Request] {
  override def apply(request: Request, wallet: Wallet): Option[Roulette] = {
    request match {
      case AddBet(bet) =>
        wallet.wager(bet.amount)
        Some(Roulette(random, pocket, bet :: bets))
      case Spin =>
        val newPocket = random()
        bets.map {
          case NumberBet(amount, pocket1) => if (pocket1 == pocket) Some(amount * 10) else None
          case _ => None
        }.foreach { case Some(payout) => wallet.payout(payout) }
        Some(Roulette(random, newPocket, List()))
      case _ => throw new AssertionError
    }
  }
}
