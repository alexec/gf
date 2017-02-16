package gf.roulette

import org.junit.Test

class PackageTest {

  @Test def pocketCannotBeZero(): Unit = {
    Pocket(0)
  }

  @Test def pocketCannotBe36(): Unit = {
    Pocket(36)
  }

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBeNegative(): Unit = {
    Pocket(-1)
  }

  @Test(expected = classOf[IllegalArgumentException]) def pocketCannotBe37(): Unit = {
    Pocket(37)
  }
}
