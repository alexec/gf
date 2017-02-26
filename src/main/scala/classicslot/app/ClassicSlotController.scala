package classicslot.app

import java.net.URI

import classicslot.infra.ClassicSlotRepo
import core.infra.HttpWallet
import core.model.Money
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/games/classic-slot"))
class ClassicSlotController(repo: ClassicSlotRepo) {

  @GetMapping
  def get(@RequestHeader("PlayerId") playerId: String, @RequestHeader("Wallet") uri: URI): Any = repo.get(playerId, new HttpWallet(uri))


  @PostMapping(Array("/spins"))
  @ResponseStatus(HttpStatus.CREATED)
  def spin(@RequestHeader("PlayerId") playerId: String, @RequestHeader("Wallet") uri: URI, @RequestParam("amount") amount: Money): Any = {
    val wallet = new HttpWallet(uri)
    val slot = repo.get(playerId, wallet).spin(amount)
    Map(
      "stops" -> slot.stops,
      "balance" -> wallet.getBalance
    )
  }
}
