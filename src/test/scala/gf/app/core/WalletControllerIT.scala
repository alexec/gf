package gf.app.core

import gf.app.IntegrationTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.notNullValue
import org.junit.{Before, Test}

class WalletControllerIT extends IntegrationTest {

  @Before override def before(): Unit = {
    super.before()

    RestAssured.basePath = "/wallet"
  }

  @Test def balances(): Unit = {
    given()
      .when()
      .get()
      .`then`()
      .statusCode(200)
      .body("balance", notNullValue())
  }
}
