package gf.app.slots

import gf.app.IntegrationTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.junit.Before

class SlotControllerIT extends IntegrationTest {
  @Before override def before(): Unit = {
    super.before()

    given()
      .param("balance", "1000")
      .when()
      .put("/service/wallets/0")
      .`then`()
      .statusCode(204)

    RestAssured.basePath = "/games/slot"

    given()
      .when()
      .delete()
      .`then`()
      .statusCode(204)
  }
}
