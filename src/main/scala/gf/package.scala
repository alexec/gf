

package object gf {

  trait Game[R] extends ((R, Wallet) => Option[Game[R]])

  trait GameFactory[G, S <: Serializable] {
    def toMaybeState(game: G): Option[S]

    def toGame(maybeState: Option[S]): G
  }

  type Sym = Int
  val wild: Sym = 0
  // slot
  type Reel = List[Sym]
  type Reels = List[Reel]
  type Window = List[Reel]
  type Stops = List[Int]
  // payLines
  type PayLine = List[Int]
  type PayLines = List[PayLine]
  // number of symbol (3,4,5) to payout multiplier
  type PayTable = Map[(Sym, Int), Int]
}
