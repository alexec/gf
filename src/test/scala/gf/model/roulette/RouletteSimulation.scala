package gf.model.roulette

import gf.model.core.{Money, MonteCarloSimulator}

import scala.util.Random

object RouletteSimulation extends {
  private val random = new Random()
} with MonteCarloSimulator[Roulette](
  wallet => new Roulette(Roulette.randomPocket(random), wallet),
  roulette => roulette.addBet(NumberBet(Money(1), Pocket(1 + random.nextInt(Pocket.MAX - 1)))) spin(),
  expectedReturnToPlayer = 0.973,
  expectedRange = 0.01
) {

  def main(args: Array[String]): Unit = {
    RouletteSimulation()
  }
}
