package core.infra

import java.net.URI

import core.model.{Money, Wallet}
import org.springframework.web.client.{HttpClientErrorException, RestTemplate}

import scala.beans.BeanProperty

class TransactionDao {
  @BeanProperty var amount: Money = _
}

class WalletDao {
  @BeanProperty var balance: Money = _
}

class HttpWallet(url: URI) extends Wallet {
  private val rest = new RestTemplate()

  override def wager(amount: Money): Unit = createTransaction(-amount)

  override def payout(amount: Money): Unit = createTransaction(amount)

  private def createTransaction(amount: Money) = {
    val transaction = new TransactionDao()
    transaction.amount = amount
    try {
      rest.postForObject(url + "/transactions", transaction, classOf[WalletDao])
    } catch {
      case _: HttpClientErrorException => throw new NotEnoughFundsException();
    }
  }

  override def getBalance: Money = rest.getForObject(url, classOf[WalletDao]).balance
}
