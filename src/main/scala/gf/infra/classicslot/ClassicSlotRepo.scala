package gf.infra.classicslot

import java.security.SecureRandom

import com.mongodb.{MongoClient, WriteConcern}
import gf.infra.core.{GameFactory, GameRepo}
import gf.model.classicslot.ClassicSlot
import gf.model.core.Wallet
import gf.model.slot.{Reels, Slot, Stops}


private object ClassicSlotFactory extends GameFactory[Slot, State] {
  private val randomStops: (Reels => Stops) = reels => reels.map { reel => new SecureRandom().nextInt(reel.length) }

  override def toMaybeState(game: Slot): Option[State] = {
    Some(State(game.stops))
  }

  override def toGame(wallet: Wallet, maybeState: Option[State]): Slot = {
    maybeState
      .map { state => ClassicSlot(randomStops, wallet, state.stops) }
      .getOrElse(ClassicSlot(randomStops, wallet))
  }
}

class ClassicSlotRepo(mongo: MongoClient, writeConcern: WriteConcern) extends GameRepo(mongo, "classic-slot", ClassicSlotFactory, writeConcern)
