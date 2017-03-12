package core.model

import org.junit.Test

class MoneyTest {

  @Test def moneyCannotBe2dp(): Unit = Money(0.01)

  @Test(expected = classOf[IllegalArgumentException]) def moneyCannotBe3dp(): Unit = Money(0.001)

}
