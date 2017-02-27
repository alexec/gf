package core.infra

import java.net.URI

import core.model.{Money, Wallet}
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.HttpClientErrorException

import scala.beans.BeanProperty

class TransactionDao {
  @BeanProperty var amount: Money = _
}

class WalletDao {
  @BeanProperty var balance: Money = _
}

class HttpWallet(url: URI, username: String, password: String) extends Wallet {
  private val rest = new RestTemplateBuilder().basicAuthorization(username, password).build()

  override def wager(amount: Money): Unit = createTransaction(-amount)

  override def payout(amount: Money): Unit = createTransaction(amount)

  private def createTransaction(amount: Money) = {
    val transaction = new TransactionDao()
    transaction.amount = amount
    try {
      rest.postForObject(url + "/transactions", transaction, classOf[WalletDao])
    } catch {
      case e: HttpClientErrorException =>
        if (e.getResponseBodyAsString.contains("not enough funds")) throw new NotEnoughFundsException() else throw e
    }
  }

  override def getBalance: Money = rest.getForObject(url, classOf[WalletDao]).balance
}
