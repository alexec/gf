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
  private val wallet = new HttpWallet(URI.create("http://localhost:9090"))

  @Before def before(): Unit = {
    wireMockServer.start()
    WireMock.configureFor(9090)
  }

  @After def tearDown(): Unit = wireMockServer.stop()

  @Test
  def balance(): Unit = {
    stubFor(get(urlEqualTo("/"))
      .withHeader("Accept", containing("application/json"))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"balance\": 1000}")))
    assertEquals(Money(1000), wallet.getBalance)
  }

  @Test
  def wagerSendNegativeAmount(): Unit = {
    stubFor(post(urlEqualTo("/transactions"))
      .withRequestBody(containing("\"amount\":-1"))
      .willReturn(aResponse()
        .withStatus(204)
        .withHeader("Content-Type", "application/json")))
    wallet.wager(Money(1))
  }

  @Test
  def payoutSendsPositiveAmount(): Unit = {
    stubFor(post(urlEqualTo("/transactions"))
      .withRequestBody(containing("\"amount\":1"))
      .willReturn(aResponse()
        .withStatus(204)
        .withHeader("Content-Type", "application/json")))
    wallet.payout(Money(1))
  }
}
