package gf

import org.specs2.mutable._
import scala.util.Random

class SlotSpec extends Specification {
  private val slot = new TestSlotFactory(() => new Random(0)).toGame(None)(Spin(BigDecimal(0)), NullWallet).get

  "slot" should {
    "have predicable stops" in {
      slot.stops must beEqualTo(List(6, 3, 1, 2, 4))
    }
    "have predicable window" in {
      slot.window must beEqualTo(List(List(9, 8, 4), List(6, 8, 1), List(1, 8, 2), List(2, 8, 5), List(6, 0, 1)))
    }
    "have predicatable payouts" in {
      slot.payouts must beEqualTo(List((List(1, 1, 1, 1, 1), 3)))
    }
  }
}
