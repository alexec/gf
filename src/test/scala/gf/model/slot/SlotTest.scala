package gf.model.slot

import gf.model.core.SimpleWallet
import org.junit.Assert._
import org.junit.Test

class SlotTest {

  private val reels = List(
    List(4, 1, 2, 6, 3, 7, 9, 8, 4),
    List(8, 1, 2, 6),
    List(8, 1, 8, 2, 5, 3),
    List(6, 1, 2, 8, 5, 6, 2),
    List(4, 1, 2, 9, 6, 0, 1)
  )

  private val payLines = List(List(1, 1, 1, 1, 1))

  private val payTable = Map(
    (8, 5) -> 3
  )

  private val stops = List(6, 3, 1, 2, 4)
  private val wallet = SimpleWallet()
  private val slot = Slot(_ => stops, wallet, reels, payTable, payLines)
    .spin(BigDecimal(1))

  @Test
  def balanceReduced() = assertEquals(BigDecimal(1002), wallet.balance)

  @Test
  def stopsAreSame() = assertEquals(stops, slot.stops)

  @Test
  def windowIsAsExpected() = assertEquals(List(List(9, 8, 4), List(6, 8, 1), List(1, 8, 2), List(2, 8, 5), List(6, 0, 1)), slot.window)

  @Test
  def payoutsAreAsExpected() = assertEquals(List((List(1, 1, 1, 1, 1), 3)), slot.payouts)
}
