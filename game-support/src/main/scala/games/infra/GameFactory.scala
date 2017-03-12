package games.infra

import games.model.Wallet

trait GameFactory[G, S] {
  def toMaybeState(game: G): Option[S]

  def toGame(wallet: Wallet, maybeState: Option[S]): G
}
