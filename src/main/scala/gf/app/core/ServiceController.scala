package gf.app.core

import gf.infra.core.DemoWallet
import gf.model.core.{Money, Wallet}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/service"))
class ServiceController(wallet: Wallet) {

  @GetMapping(Array("/wallet"))
  def getWallet: Any = Map("balance" -> wallet.getBalance)

  @PutMapping(Array("/wallet"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def setBalance(@RequestParam balance: Money): Unit = {
    wallet.asInstanceOf[DemoWallet].setBalance(balance)
  }
}
