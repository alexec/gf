package gf.model.slot

import gf.model.core.{Money, Wallet}


case class Slot(
                 random: Reels => Stops,
                 wallet: Wallet,
                 reels: Reels,
                 payTable: PayTable,
                 payLines: PayLines,
                 stops: Stops = List(0, 0, 0, 0, 0)
               ) {

  val window: Window = stops
    .zip(reels)
    .map {
      case (stop, reel) => reel.slice(stop, stop + 3) ::: reel.take(3 - reel.length + stop)
    }
  val payouts: List[(PayLine, Int)] = payLines
    // extract the line
    .map {
    line =>
      (line, line
        .zip(window)
        .map {
          case (stop, reel) =>
            reel(stop)
        })
  }
    // determine how many symbols match
    .map {
    case (line, syms) =>
      val sym = syms.find(sym => sym != wild).getOrElse(wild)
      (line, syms
        .foldLeft((sym, 0)) {
          case ((sym: Sym, count: Int), nextSym) =>
            (sym, if (sym == nextSym || nextSym == wild) count + 1 else 0)
        })
  }
    // find the pay table entry
    .map { case (line, x) => (line, payTable.get(x)) }
    .filter { case (_, x) => x.isDefined }
    .map { case (line, x) => (line, x.get) }

  def spin(amount: Money): Slot = {

    wallet.wager(amount)

    val slot = copy(stops = random(reels))

    slot.payouts.foreach { case (_, b) => wallet.payout(amount * b) }

    slot
  }


}
