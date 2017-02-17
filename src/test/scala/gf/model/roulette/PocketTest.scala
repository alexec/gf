package gf.model.roulette

import org.junit.Assert._
import org.junit.Test

class PocketTest {

  @Test def pocketCanBeZero(): Unit = Pocket(0)

  @Test def pocketCanBe36(): Unit = Pocket(36)

  @Test def pocket0IsNotRed(): Unit = assertFalse(Pocket(0).isRed)

  @Test def pocket19IsRed(): Unit = assertTrue(Pocket(19).isRed)

  @Test def pocket33IsNotRed(): Unit = assertFalse(Pocket(33).isRed)

  @Test def pocket0IsNotBlack(): Unit = assertFalse(Pocket(0).isBlack)

  @Test def pocket19IsNotRed(): Unit = assertFalse(Pocket(19).isBlack)

  @Test def pocket33IsBlack(): Unit = assertTrue(Pocket(33).isBlack)

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBeNegative(): Unit = Pocket(-1)

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBe37(): Unit = Pocket(37)
}
