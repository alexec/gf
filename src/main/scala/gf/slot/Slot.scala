package gf.slot

import gf.core.{Game, Sym, Wallet, wild}

import scala.util.Random

trait Request

case class Spin(amount: BigDecimal) extends Request

object Bonus extends Request

case class Slot(
                 random: Random,
                 reels: Reels,
                 payTable: PayTable,
                 payLines: PayLines,
                 stops: Stops = List(0, 0, 0, 0, 0)
               ) extends Game[Request] {

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
          case ((sym: Sym, count: Int), nextSym) => {
            (sym, if (sym == nextSym || nextSym == wild) count + 1 else 0)
          }
        })
  }
    // find the pay table entry
    .map { case (line, x) => (line, payTable.get(x)) }
    .filter { case (_, x) => x.isDefined }
    .map { case (line, x) => (line, x.get) }

  override def apply(event: Request, wallet: Wallet): Option[Slot] = {

    event match {
      case Spin(amount) => {

        wallet.wager(amount)

        Some(Slot(
          random,
          reels,
          payTable,
          payLines,
          reels.map(reel => random.nextInt(reel.length))
        ))
      }
      // case Bonus => ???
      case _ => throw new AssertionError
    }
  }
}
