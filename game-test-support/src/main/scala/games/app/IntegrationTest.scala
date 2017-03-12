package games.app

import java.net.URI

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import games.App
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.junit.{After, Before}

abstract class IntegrationTest(app: App) {
  private val server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:8080"), app)
  private val wireMockServer = new WireMockServer(9090)
  var spec: RequestSpecBuilder = _

  @Before def before(): Unit = {

    server.start()

    RestAssured.reset()
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

    spec = new RequestSpecBuilder()
      .addHeader("PlayerId", "0")
      .addHeader("Wallet", "http://localhost:9090")

    wireMockServer.start()
    WireMock.configureFor(9090)

    stubFor(get(urlEqualTo("/"))
      .withHeader("Accept", containing("application/json"))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"balance\": 1000}")))

    stubFor(post(urlEqualTo("/transactions"))
      .willReturn(aResponse()
        .withStatus(201)
        .withHeader("Content-Type", "application/json")))
  }

  @After
  def tearDown(): Unit = {
    wireMockServer.stop()

    server.shutdown()
  }
}
