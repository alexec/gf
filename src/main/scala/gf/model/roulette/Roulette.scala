package gf.model.roulette

import gf.model.core.Wallet

import scala.beans.BeanProperty
import scala.util.Random




object Roulette {
  def randomPocket(random: Random): () => Pocket = () => Pocket(random.nextInt(37))
}

case class Roulette(random: () => Pocket, wallet: Wallet, @BeanProperty pocket: Pocket = Pocket(0), @BeanProperty bets: List[Bet] = List()) {
  def addBet(bet: Bet): Roulette = {
    wallet.wager(bet.amount)
    copy(bets = bet :: bets)
  }

  def spin(): Roulette = {
    val roulette = copy(pocket = random(), bets = List())
    bets.map {
      _.payout(roulette.pocket)
    }
      .foreach {
        wallet.payout
      }
    roulette
  }
}
