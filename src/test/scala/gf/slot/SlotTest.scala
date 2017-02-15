package gf.slot

import gf.core.NullWallet
import org.junit.Assert._
import org.junit.Test

class SlotTest {
  private val stops = List(6, 3, 1, 2, 4)
  private val slot = new TestSlotFactory(_ => stops).toGame(None)(Spin(BigDecimal(0)), NullWallet).get

  @Test
  def stopsAreSame() = assertEquals(stops, slot.stops)

  @Test
  def windowIsAsExpected() = assertEquals(List(List(9, 8, 4), List(6, 8, 1), List(1, 8, 2), List(2, 8, 5), List(6, 0, 1)), slot.window)

  @Test
  def payoutsAreAsExpected() = assertEquals(List((List(1, 1, 1, 1, 1), 3)), slot.payouts)
}
