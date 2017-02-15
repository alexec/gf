package gf.slot

import gf.core.NullWallet
import org.junit.Assert._
import org.junit.Test

class SlotTest {
  private val slot = new TestSlotFactory(_ => List(6, 3, 1, 2, 4)).toGame(None)(Spin(BigDecimal(0)), NullWallet).get

  @Test
  def stops() = assertEquals(List(6, 3, 1, 2, 4), slot.stops)

  @Test
  def window() = assertEquals(List(List(9, 8, 4), List(6, 8, 1), List(1, 8, 2), List(2, 8, 5), List(6, 0, 1)), slot.window)

  @Test
  def payouts() = assertEquals(List((List(1, 1, 1, 1, 1), 3)), slot.payouts)
}
