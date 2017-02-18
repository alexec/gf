package gf.infra

package object core {

  trait GameFactory[G, S] {
    def toMaybeState(game: G): Option[S]

    def toGame(maybeState: Option[S]): G
  }

}
