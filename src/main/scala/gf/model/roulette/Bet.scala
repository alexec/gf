package gf.model.roulette

import scala.beans.BeanProperty


sealed trait Bet {
  @BeanProperty val amount: BigDecimal

  require(amount > 0)

  def payout(pocket: Pocket): BigDecimal = amount * (payoutMultiplier(pocket) match {
    case 0 => 0
    case m => m + 1
  })

  protected def payoutMultiplier(pocket: Pocket): Int
}

case class NumberBet(@BeanProperty amount: BigDecimal, pocket: Pocket) extends Bet {
  require(pocket.number > 0)

  override protected def payoutMultiplier(pocket: Pocket): Int = if (pocket == this.pocket) 35 else 0
}

case class RedBet(@BeanProperty amount: BigDecimal) extends Bet {
  override protected def payoutMultiplier(pocket: Pocket): Int = if (pocket.isRed) 1 else 0
}

case class BlackBet(@BeanProperty amount: BigDecimal) extends Bet {
  override protected def payoutMultiplier(pocket: Pocket): Int = if (pocket.isBlack) 1 else 0
}