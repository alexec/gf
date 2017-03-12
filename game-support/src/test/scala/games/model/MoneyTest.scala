package games.model

import org.junit.Assert.assertEquals
import org.junit.Test

class MoneyTest {

  @Test def moneyCanBe2dp(): Unit = assertEquals(BigDecimal(0.01).bigDecimal, Money(0.01).bigDecimal)

  @Test(expected = classOf[IllegalArgumentException]) def moneyCannotBe3dp(): Unit = Money(0.001)

}
