package gf.infra.roulette

import java.security.SecureRandom

import com.mongodb.{MongoClient, WriteConcern}
import gf.infra.core.{GameFactory, GameRepo}
import gf.model.core.Wallet
import gf.model.roulette.Roulette


private object RouletteGameFactory extends GameFactory[Roulette, State] {
  private val randomPocket = Roulette.randomPocket(new SecureRandom())

  override def toMaybeState(game: Roulette): Option[State] = {
    Some(State(game.pocket, game.bets))
  }

  override def toGame(wallet: Wallet, maybeState: Option[State]): Roulette = {
    maybeState
      .map { state => new Roulette(randomPocket, wallet, state.pocket, state.bets) }
      .getOrElse(new Roulette(randomPocket, wallet))
  }
}

class RouletteRepo(mongo: MongoClient, writeConcern: WriteConcern) extends GameRepo(mongo, "roulette", RouletteGameFactory, writeConcern)
