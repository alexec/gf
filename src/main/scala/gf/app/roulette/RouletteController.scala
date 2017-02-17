package gf.app.roulette

import java.math.MathContext

import gf.infra.RouletteRepo
import gf.model.core.Money
import gf.model.roulette.{NumberBet, Pocket, Roulette}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
class RouletteController(@Autowired val repo: RouletteRepo) {

  @RequestMapping(Array("/roulette"))
  def get(): Roulette = {
    repo.get()
  }

  @RequestMapping(value = Array("/roulette/bets/numbers"), method = Array(RequestMethod.POST))
  @ResponseStatus(HttpStatus.CREATED)
  def addNumbersBet(@RequestParam("amount") amount: String, @RequestParam("number") number: Int): Unit = {
    repo.put(repo.get().addBet(NumberBet(Money(BigDecimal(amount, MathContext.UNLIMITED)), Pocket(number))))
  }
}
