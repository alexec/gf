package gf

package object core {

  type Sym = Int
  val wild: Sym = 0

  trait Game

  trait GameFactory[G <: Game, S <: Serializable] {
    def toMaybeState(game: G): Option[S]

    def toGame(maybeState: Option[S]): G
  }

}
