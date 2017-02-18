package gf.infra.roulette

import java.security.SecureRandom

import com.mongodb.MongoClient
import gf.infra.core.{GameFactory, GameRepo}
import gf.model.core.NullWallet
import gf.model.roulette.Roulette


private object RouletteGameFactory extends GameFactory[Roulette, State] {
  private val randomPocket = Roulette.randomPocket(new SecureRandom())

  override def toMaybeState(game: Roulette): Option[State] = {
    Some(State(game.pocket, game.bets))
  }

  override def toGame(maybeState: Option[State]): Roulette = {
    maybeState
      .map { state => new Roulette(randomPocket, NullWallet, state.pocket, state.bets) }
      .getOrElse(new Roulette(randomPocket, NullWallet))
  }
}

class RouletteRepo(mongo: MongoClient) extends GameRepo(mongo, "roulette", RouletteGameFactory)
