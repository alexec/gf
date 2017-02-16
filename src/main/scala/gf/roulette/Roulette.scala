package gf.roulette

import gf.core.Wallet


sealed case class Pocket(number: Int) {
  require(number >= 0)
  require(number <= 36)

  def isBlack: Boolean = number > 0 && !isRed

  def isRed: Boolean = List(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36).contains(number)

  // def isColumn(col: Int): Boolean = {
  //  Map(
  //    1 -> List(1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34),
  //   2 -> List(2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35),
  //   3 -> List(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36)
  //  )
  //}.get(col).contains(number)
}

sealed trait Bet {
  val amount: BigDecimal

  require(amount > 0)

  def payout(pocket: Pocket): BigDecimal = amount * (payoutMultiplier(pocket) match {
    case 0 => 0
    case m => m + 1
  })

  protected def payoutMultiplier(pocket: Pocket): Int
}

case class NumberBet(amount: BigDecimal, pocket: Pocket) extends Bet {
  require(pocket.number > 0)

  override protected def payoutMultiplier(pocket: Pocket): Int = if (pocket == this.pocket) 35 else 0
}

case class RedBet(amount: BigDecimal) extends Bet {
  override protected def payoutMultiplier(pocket: Pocket): Int = if (pocket.isRed) 1 else 0
}

case class BlackBet(amount: BigDecimal) extends Bet {
  override protected def payoutMultiplier(pocket: Pocket): Int = if (pocket.isBlack) 1 else 0
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
