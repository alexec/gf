package gf

package object core {

  type Sym = Int
  val wild: Sym = 0

  trait Game[R] extends ((R, Wallet) => Option[Game[R]])

  trait GameFactory[G, S <: Serializable] {
    def toMaybeState(game: G): Option[S]

    def toGame(maybeState: Option[S]): G
  }
}
