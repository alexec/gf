package gf.app.roulette

import gf.infra.roulette.RouletteRepo
import gf.model.core.{Money, NullWallet}
import gf.model.roulette._
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/roulette"))
class RouletteController(repo: RouletteRepo) {
  private val wallet = NullWallet

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(): Unit = repo.delete()


  @GetMapping
  def get(): Any = {
    val roulette = repo.get(wallet)
    Map(
      "pocket" -> roulette.pocket.number,
      "bets" -> roulette.bets.map {
        case NumberBet(amount, pocket) => Map("type" -> "number", "number" -> pocket.number, "amount" -> amount)
        case RedBet(amount) => Map("type" -> "red", "amount" -> amount)
        case BlackBet(amount) => Map("type" -> "black", "amount" -> amount)
      }
    )
  }

  @PostMapping(Array("/bets/numbers"))
  @ResponseStatus(HttpStatus.CREATED)
  def addNumbersBet(@RequestParam("amount") amount: Money, @RequestParam("number") number: Int): Any =
    addBet(NumberBet(amount, Pocket(number)))

  private def addBet(bet: Bet) = {
    repo.set(repo.get(wallet).addBet(bet))
    Map("balance" -> wallet.getBalance)
  }

  @PostMapping(Array("/bets/red"))
  @ResponseStatus(HttpStatus.CREATED)
  def addRedBet(@RequestParam("amount") amount: Money): Any = addBet(RedBet(amount))

  @PostMapping(Array("/bets/black"))
  @ResponseStatus(HttpStatus.CREATED)
  def addBlackBet(@RequestParam("amount") amount: Money): Any = addBet(BlackBet(amount))

  @PutMapping(Array("/spin"))
  def spin(): Any = {
    repo.set(repo.get(wallet).spin())
    Map("balance" -> wallet.getBalance)
  }
}
