package games

import classicslot.model.ClassicSlotSimulation
import roulette.model.RouletteSimulation

object Simulate {

  def main(args: Array[String]): Unit = {
    Array(
      RouletteSimulation,
      ClassicSlotSimulation
    )
      .map { simulator =>

        try {
          simulator.main(args)
          None
        } catch {
          case e: Throwable => Some(simulator -> e)
        }
      }
      .filter {
        _.isDefined
      }
      .map {
        _.get
      }
      .map { failure => println("failure " + failure); failure }
      .map { _ => System.exit(1) }
  }
}
