package gf.infra.core

import java.net.URI

import gf.model.core.{Money, Wallet}
import org.springframework.web.client.RestTemplate

import scala.beans.BeanProperty

class TransactionDao {
  @BeanProperty var amount: Money = _
}

class WalletDao {
  @BeanProperty var balance: Money = _
}

class HttpWallet(url: URI) extends Wallet {
  private val rest = new RestTemplate()

  override def wager(amount: Money): Unit = createTransaction(amount)

  private def createTransaction(amount: Money) = {
    val transaction = new TransactionDao()
    transaction.amount = amount
    rest.postForObject(url + "/transactions", transaction, classOf[WalletDao])
  }

  override def payout(amount: Money): Unit = createTransaction(-amount)

  override def getBalance: Money = rest.getForObject(url, classOf[WalletDao]).balance
}
