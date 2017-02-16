package gf

package object roulette {

  type Pocket = Int

  object Pocket {
    def apply(pocket: Pocket): Pocket = {
      require(pocket >= 0)
      require(pocket <= 36)
      pocket
    }
  }

}
