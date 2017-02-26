package roulette.app

import java.net.URI

import core.infra.HttpWallet
import core.model.Money
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._
import roulette.infra.RouletteRepo
import roulette.model._

@RestController
@RequestMapping(Array("/games/roulette"))
class RouletteController(repo: RouletteRepo) {

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(): Unit = repo.delete()


  @GetMapping
  def get(@RequestHeader("Wallet") uri: URI): Any = {
    val roulette = repo.get(new HttpWallet(uri))
    Map(
      "pocket" -> roulette.pocket.number,
      "bets" -> roulette.bets.map {
        case NumberBet(amount, pocket) => Map("type" -> "number", "number" -> pocket.number, "amount" -> amount)
        case RedBet(amount) => Map("type" -> "red", "amount" -> amount)
        case BlackBet(amount) => Map("type" -> "black", "amount" -> amount)
        case _ => throw new AssertionError()
      }
    )
  }

  @PostMapping(Array("/bets/number"))
  @ResponseStatus(HttpStatus.CREATED)
  def addNumbersBet(@RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money, @RequestParam("number") number: Int): Any =
    addBet(uri, NumberBet(amount, Pocket(number)))

  @PostMapping(Array("/bets/red"))
  @ResponseStatus(HttpStatus.CREATED)
  def addRedBet(@RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money): Any =
    addBet(uri, RedBet(amount))

  @PostMapping(Array("/bets/black"))
  @ResponseStatus(HttpStatus.CREATED)
  def addBlackBet(@RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money): Any =
    addBet(uri, BlackBet(amount))

  private def addBet(uri: URI, bet: Bet) = {
    val wallet = new HttpWallet(uri)
    repo.set(repo.get(wallet).addBet(bet))
    Map("balance" -> wallet.getBalance)
  }

  @PostMapping(Array("/spins"))
  def spin(@RequestHeader("Wallet") uri: URI): Any = {
    val wallet = new HttpWallet(uri)
    val roulette = repo.get(wallet).spin()
    repo.set(roulette)
    Map(
      "balance" -> wallet.getBalance,
      "pocket" -> roulette.pocket.number
    )
  }
}
