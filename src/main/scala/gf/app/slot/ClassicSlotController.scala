package gf.app.slot

import gf.infra.slot.classic.ClassicSlotRepo
import gf.model.core.Wallet
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/games/slots/classic"))
class ClassicSlotController(repo: ClassicSlotRepo, wallet: Wallet) {

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(): Unit = repo.delete()

  @GetMapping
  def get(): Any = repo.get(wallet)
}
