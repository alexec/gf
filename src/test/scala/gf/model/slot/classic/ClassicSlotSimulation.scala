package gf.model.slot.classic

import gf.model.core.{Money, MonteCarloSimulator}
import gf.model.slot.Slot

object ClassicSlotSimulation extends MonteCarloSimulator[Slot](
  wallet => ClassicSlot(Slot.randomStops, wallet),
  slot => slot.spin(Money(10)),
  expectedReturnToPlayer = 0.95,
  expectedRange = 0.01
)
