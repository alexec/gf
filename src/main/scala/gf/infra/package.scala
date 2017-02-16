package gf

package object infra {

  trait GameFactory[G, S <: Serializable] {
    def toMaybeState(game: G): Option[S]

    def toGame(maybeState: Option[S]): G
  }

}
