package gf.app.classicslot

import gf.infra.classicslot.ClassicSlotRepo
import gf.model.core.{Money, Wallet}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/games/classic-slot"))
class ClassicSlotController(repo: ClassicSlotRepo, wallet: Wallet) {

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(): Unit = repo.delete()

  @GetMapping
  def get(): Any = repo.get(wallet)


  @PostMapping(Array("/spins"))
  @ResponseStatus(HttpStatus.CREATED)
  def spin(@RequestParam("amount") amount: Money): Any = {
    val slot = repo.get(wallet).spin(amount)
    Map(
      "stops" -> slot.stops,
      "balance" -> wallet.getBalance
    )
  }
}
