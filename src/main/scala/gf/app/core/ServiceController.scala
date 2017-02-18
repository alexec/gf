package gf.app.core

import gf.model.core.NullWallet
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/service"))
class ServiceController {
  private val wallet = NullWallet

  @GetMapping(Array("/wallet"))
  def getWallet: Any = Map("balance" -> wallet.getBalance)
}
