package gf.app

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import gf.Config
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import org.junit.runner.RunWith
import org.junit.{After, Before}
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@ContextConfiguration(classes = Array(classOf[Config]))
@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class IntegrationTest {
  private val wireMockServer = new WireMockServer(9090)
  @LocalServerPort var port: Int = _
  var spec: RequestSpecBuilder = _

  @Before def before(): Unit = {
    RestAssured.reset()
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

    spec = new RequestSpecBuilder()
      .setPort(port)
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
      .withRequestBody(containing("amount"))
      .willReturn(aResponse()
        .withStatus(204)
        .withHeader("Content-Type", "application/json")))
  }

  @After
  def tearDown(): Unit =
    wireMockServer.stop()
}
