package core

package object model {
  type Money = BigDecimal

  object Money {
    def apply(amount: BigDecimal): Money = {
      require(amount.scale <= 2)
      amount
    }
  }

}
