package gf.infra

import gf.model.core.Wallet

package object core {

  trait GameFactory[G, S] {
    def toMaybeState(game: G): Option[S]

    def toGame(wallet: Wallet, maybeState: Option[S]): G
  }

}
