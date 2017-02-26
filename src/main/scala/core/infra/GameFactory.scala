package core

import core.model.Wallet

package object infra {

  trait GameFactory[G, S] {
    def toMaybeState(game: G): Option[S]

    def toGame(wallet: Wallet, maybeState: Option[S]): G
  }

}
