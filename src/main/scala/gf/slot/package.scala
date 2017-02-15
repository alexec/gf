package gf

import gf.core.Sym

package object slot {
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
