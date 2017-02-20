package gf.app.core

import gf.app.IntegrationTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.notNullValue
import org.junit.{Before, Test}

class ServiceControllerIT extends IntegrationTest {

  @Before override def before(): Unit = {
    super.before()

    RestAssured.basePath = "/service"
  }

  @Test def balances(): Unit = {
    given()
      .when()
      .get("/wallets/0")
      .`then`()
      .statusCode(200)
      .body("balance", notNullValue())
  }
}
