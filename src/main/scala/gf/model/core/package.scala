package gf.model

package object core {
  type Money = BigDecimal

  object Money {
    def apply(amount: BigDecimal): Money = {
      require(amount.scale <= 2)
      amount
    }
  }

}
