package gf.app.roulette

import gf.infra.RouletteRepo
import gf.model.core.Money
import gf.model.roulette.{NumberBet, Pocket, Roulette}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController("/roulette")
class RouletteController(@Autowired val repo: RouletteRepo) {

  @GetMapping()
  def get(): Roulette = {
    repo.get()
  }

  @RequestMapping(value = Array("/bets/numbers"), method = Array(RequestMethod.POST))
  @ResponseStatus(HttpStatus.CREATED)
  def addNumbersBet(@RequestParam("amount") amount: Money, @RequestParam("number") number: Int): Unit = {
    repo.put(repo.get().addBet(NumberBet(amount, Pocket(number))))
  }
}
