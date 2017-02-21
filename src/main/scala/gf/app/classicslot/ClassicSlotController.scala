package gf.app.classicslot

import java.net.URI

import gf.infra.classicslot.ClassicSlotRepo
import gf.infra.core.HttpWallet
import gf.model.core.Money
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/games/classic-slot"))
class ClassicSlotController(repo: ClassicSlotRepo) {

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(): Unit = repo.delete()

  @GetMapping
  def get(@RequestHeader("Wallet") uri: URI): Any = repo.get(new HttpWallet(uri))


  @PostMapping(Array("/spins"))
  @ResponseStatus(HttpStatus.CREATED)
  def spin(@RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money): Any = {
    val wallet = new HttpWallet(uri)
    val slot = repo.get(wallet).spin(amount)
    Map(
      "stops" -> slot.stops,
      "balance" -> wallet.getBalance
    )
  }
}
