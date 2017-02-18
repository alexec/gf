package gf.app.roulette

import java.math.MathContext

import com.fasterxml.jackson.databind.ObjectMapper
import gf.infra.roulette.RouletteRepo
import gf.model.core.Money
import gf.model.roulette.{NumberBet, Pocket}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/roulette"))
class RouletteController(@Autowired repo: RouletteRepo, @Autowired mapper: ObjectMapper) {

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(): Unit = repo.delete()


  @GetMapping
  def get(): Any = {
    val roulette = repo.get()
    Map(
      "pocket" -> roulette.pocket.number,
      "bets" -> roulette.bets.map {
        case NumberBet(amount, pocket) => Map("type" -> "number", "number" -> pocket.number, "amount" -> amount)
        case _ => Map()
      }
    )
  }

  @PostMapping(Array("/bets/numbers"))
  @ResponseStatus(HttpStatus.CREATED)
  def addNumbersBet(@RequestParam("amount") amount: String, @RequestParam("number") number: Int): Unit = {
    repo.set(repo.get().addBet(NumberBet(Money(BigDecimal(amount, MathContext.UNLIMITED)), Pocket(number))))
  }
}
