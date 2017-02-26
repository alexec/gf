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

  @GetMapping
  def get(@RequestHeader("PlayerId") playerId: String, @RequestHeader("Wallet") uri: URI): Any = {
    val roulette = repo.get(playerId, new HttpWallet(uri))
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
  def addNumbersBet(@RequestHeader("PlayerId") playerId: String, @RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money, @RequestParam("number") number: Int): Any =
    addBet(playerId, uri, NumberBet(amount, Pocket(number)))

  private def addBet(playerId: String, uri: URI, bet: Bet) = {
    val wallet = new HttpWallet(uri)
    repo.set(playerId, repo.get(playerId, wallet).addBet(bet))
    Map("balance" -> wallet.getBalance)
  }

  @PostMapping(Array("/bets/red"))
  @ResponseStatus(HttpStatus.CREATED)
  def addRedBet(@RequestHeader("PlayerId") playerId: String, @RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money): Any =
    addBet(playerId, uri, RedBet(amount))

  @PostMapping(Array("/bets/black"))
  @ResponseStatus(HttpStatus.CREATED)
  def addBlackBet(@RequestHeader("PlayerId") playerId: String, @RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money): Any =
    addBet(playerId, uri, BlackBet(amount))

  @PostMapping(Array("/spins"))
  def spin(@RequestHeader("PlayerId") playerId: String, @RequestHeader("Wallet") uri: URI): Any = {
    val wallet = new HttpWallet(uri)
    val roulette = repo.get(playerId, wallet).spin()
    repo.set(playerId, roulette)
    Map(
      "balance" -> wallet.getBalance,
      "pocket" -> roulette.pocket.number
    )
  }
}
