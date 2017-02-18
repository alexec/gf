package gf.app.roulette

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@ContextConfiguration(classes = Array(classOf[gf.Config]))
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RouletteControllerIT {

  @LocalServerPort var port: Int = _

  @Before def before(): Unit = {
    RestAssured.port = port
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

    given()
      .when()
      .delete("/roulette")
      .then()
      .statusCode(204)
  }

  //noinspection AccessorLikeMethodIsUnit
  @Test def getRoulette(): Unit = {
    given()
      .when()
      .get("/roulette")
      .then()
      .statusCode(200)
      .body("pocket", equalTo(0))
  }

  @Test def addBet(): Unit = {
    given()
      .param("amount", "10")
      .param("number", "19")
      .when()
      .post("/roulette/bets/numbers")
      .then()
      .statusCode(201)
    given()
      .when()
      .get("/roulette")
      .then()
      .statusCode(200)
      .body("bets[0].type", equalTo("number"))
      .body("bets[0].amount", equalTo(10))
      .body("bets[0].number", equalTo(19))
  }

}
