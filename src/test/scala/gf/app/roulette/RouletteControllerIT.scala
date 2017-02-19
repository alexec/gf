package gf.app.roulette

import gf.app.IntegrationTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.{equalTo, notNullValue}
import org.junit.{Before, Test}

class RouletteControllerIT extends IntegrationTest {

  @Before override def before(): Unit = {
    super.before()

    given()
      .param("balance", "1000")
      .when()
      .put("/service/wallet")
      .`then`()
      .statusCode(204)

    RestAssured.basePath = "/games/roulette"

    given()
      .when()
      .delete()
      .`then`()
      .statusCode(204)
  }

  @Test def balance(): Unit = {
    given()
      .get("../../service/wallet")
      .`then`()
      .statusCode(200)
      .body("balance", equalTo(1000))

    given()
      .param("amount", "10")
      .param("number", "19")
      .when()
      .post("/bets/numbers")
      .`then`()
      .statusCode(201)

    given()
      .get("../../service/wallet")
      .`then`()
      .statusCode(200)
      .body("balance", equalTo(990))
  }

  //noinspection AccessorLikeMethodIsUnit
  @Test def getRoulette(): Unit = {
    given()
      .when()
      .get()
      .`then`()
      .statusCode(200)
      .body("pocket", equalTo(0))
  }

  @Test def addNumberBet(): Unit = {
    given()
      .param("amount", "10")
      .param("number", "19")
      .when()
      .post("/bets/numbers")
      .`then`()
      .statusCode(201)
      .body("balance", notNullValue())
    given()
      .when()
      .get()
      .`then`()
      .statusCode(200)
      .body("bets[0].type", equalTo("number"))
      .body("bets[0].amount", equalTo(10))
      .body("bets[0].number", equalTo(19))
  }

  @Test def addRedBet(): Unit = {
    given()
      .param("amount", "10")
      .when()
      .post("/bets/red")
      .`then`()
      .statusCode(201)
      .body("balance", notNullValue())
    given()
      .when()
      .get()
      .`then`()
      .statusCode(200)
      .body("bets[0].type", equalTo("red"))
      .body("bets[0].amount", equalTo(10))
  }

  @Test def addBlackBet(): Unit = {
    given()
      .param("amount", "10")
      .when()
      .post("/bets/black")
      .`then`()
      .statusCode(201)
      .body("balance", notNullValue())
    given()
      .when()
      .get()
      .`then`()
      .statusCode(200)
      .body("bets[0].type", equalTo("black"))
      .body("bets[0].amount", equalTo(10))
  }

  @Test def spin(): Unit = {
    given()
      .param("amount", "10")
      .when()
      .post("/bets/black")
      .`then`()
      .statusCode(201)
    given()
      .when()
      .put("/spin")
      .`then`()
      .statusCode(200)
      .body("balance", notNullValue())
      .body("pocket", notNullValue())
  }
}
