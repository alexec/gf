package gf.app.slots

import gf.app.IntegrationTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.{Before, Test}

class ClassicSlotControllerIT extends IntegrationTest {
  @Before override def before(): Unit = {
    super.before()

    RestAssured.basePath = "/games/slots/classic"

    given()
      .when()
      .delete()
      .`then`()
      .statusCode(204)
  }

  @Test
  def get(): Unit = {
    given()
      .when()
      .get()
      .`then`()
      .statusCode(200)
      .body("stops[0]", equalTo(0))
      .body("stops[1]", equalTo(0))
      .body("stops[2]", equalTo(0))
  }


}
