package core.infra

import java.net.URI
import javax.ws.rs.ForbiddenException
import javax.ws.rs.client.{ClientBuilder, Entity}
import javax.ws.rs.core.MediaType

import core.model.{Money, Wallet}
import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature

import scala.beans.BeanProperty

class TransactionDao {
  @BeanProperty var amount: Money = _
}

class WalletDao {
  @BeanProperty var balance: Money = _
}

class HttpWallet(url: URI, username: String, password: String) extends Wallet {
  private val basic = HttpAuthenticationFeature.basic(username, password)
  private val rest = ClientBuilder.newClient(new ClientConfig().register(basic, 0))

  override def wager(amount: Money): Unit = createTransaction(-amount)

  override def payout(amount: Money): Unit = createTransaction(amount)

  private def createTransaction(amount: Money) = {
    val transaction = new TransactionDao()
    transaction.amount = amount
    try {
      rest.target(url + "/transactions")
        .request()
        .post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE), classOf[WalletDao])
    } catch {
      case _: ForbiddenException => throw new NotEnoughFundsException()
    }
  }

  override def getBalance: Money = rest.target(url)
    .request()
    .accept(MediaType.APPLICATION_JSON_TYPE)
    .get(classOf[WalletDao])
    .balance
}
