package gf.slot

import gf.core.GameFactory

case class State(stops: Stops)

class TestSlotFactory(random: Reels => Stops) extends GameFactory[Slot, State] {

  private val reels = List(
    List(4, 1, 2, 6, 3, 7, 9, 8, 4),
    List(8, 1, 2, 6),
    List(8, 1, 8, 2, 5, 3),
    List(6, 1, 2, 8, 5, 6, 2),
    List(4, 1, 2, 9, 6, 0, 1)
  )

  private val payLines = List(List(1, 1, 1, 1, 1))

  private val payTable = Map(
    (8, 5) -> 3
  )

  override def toMaybeState(game: Slot): Option[State] = Some(State(game.stops))

  override def toGame(maybeState: Option[State]): Slot = maybeState.map(s => Slot(random, reels, payTable, payLines, s.stops)).getOrElse(Slot(random, reels, payTable, payLines))

}
