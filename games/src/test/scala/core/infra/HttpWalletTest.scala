package core.infra

import java.net.URI

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import core.model.Money
import org.junit.Assert.assertEquals
import org.junit.{After, Before, Test}

class HttpWalletTest {
  private val wireMockServer = new WireMockServer(9090)
  private val uri = URI.create("http://localhost:9090")
  private val wallet = new HttpWallet(uri, "username", "password")
  private val unauthorizedWallet = new HttpWallet(uri, "xxx", "xxx")

  @Before def before(): Unit = {
    wireMockServer.start()
    WireMock.configureFor(9090)
  }

  @After def tearDown(): Unit = {
    WireMock.reset()
    wireMockServer.stop()
  }


  @Test(expected = classOf[Exception])
  def notAuthorizedGetBalance(): Unit = {
    stubFor(get(anyUrl())
      .willReturn(aResponse().withStatus(403)))
    unauthorizedWallet.getBalance
  }

  @Test(expected = classOf[Exception])
  def notAuthorizedWager(): Unit = {
    stubFor(post(anyUrl())
      .willReturn(aResponse().withStatus(403)))
    unauthorizedWallet.wager(Money(1))
  }

  @Test(expected = classOf[Exception])
  def notAuthorizedPayout(): Unit = {
    stubFor(post(anyUrl())
      .willReturn(aResponse().withStatus(403)))
    unauthorizedWallet.payout(Money(1))
  }

  @Test
  def balance(): Unit = {
    stubFor(get(urlEqualTo("/"))
      .withHeader("Accept", containing("application/json"))
      .withBasicAuth("username", "password")
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"balance\": 1000}")))
    assertEquals(Money(1000), wallet.getBalance)
  }

  @Test(expected = classOf[NotEnoughFundsException])
  def notEnoughFunds(): Unit = {
    stubFor(post(urlEqualTo("/transactions"))
      .willReturn(aResponse()
        .withStatus(403)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"message\": \"not enough funds\"}")))
    wallet.wager(Money(1))
  }

  @Test
  def wagerSendNegativeAmount(): Unit = {
    stubFor(post(urlEqualTo("/transactions"))
      .withBasicAuth("username", "password")
      .withRequestBody(containing("\"amount\":-1"))
      .willReturn(aResponse()
        .withStatus(201)
        .withHeader("Content-Type", "application/json")))
    wallet.wager(Money(1))
  }

  @Test
  def payoutSendsPositiveAmount(): Unit = {
    stubFor(post(urlEqualTo("/transactions"))
      .withBasicAuth("username", "password")
      .withRequestBody(containing("\"amount\":1"))
      .willReturn(aResponse()
        .withStatus(201)
        .withHeader("Content-Type", "application/json")))
    wallet.payout(Money(1))
  }
}
