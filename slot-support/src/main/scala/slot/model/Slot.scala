package slot.model

import java.security.SecureRandom

import games.model.{Money, Wallet}


object Slot {
  val randomStops: Reels => Stops = _.map { reel => new SecureRandom().nextInt(reel.length) }
}
case class Slot(
                 nextStops: Reels => Stops,
                 wallet: Wallet,
                 reels: Reels,
                 payTable: PayTable,
                 payLines: PayLines,
                 height: Int,
                 stops: Stops
               ) {

  val window: Window = stops
    .zip(reels)
    .map {
      case (stop, reel) => reel.slice(stop, stop + height) ::: reel.take(height - reel.length + stop)
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

    val slot = copy(stops = nextStops(reels))

    slot.payouts.foreach { case (_, b) => wallet.payout(amount * b) }

    slot
  }


}
