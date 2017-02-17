package gf.model.core

import scala.beans.BeanProperty

sealed case class Money(@BeanProperty amount: BigDecimal) {
  require(amount.scale <= 2)
}