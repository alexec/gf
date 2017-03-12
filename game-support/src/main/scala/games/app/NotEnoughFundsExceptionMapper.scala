package games.app

import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}

import games.model.NotEnoughFundsException


@Provider
class NotEnoughFundsExceptionMapper extends ExceptionMapper[NotEnoughFundsException] {
  override def toResponse(e: NotEnoughFundsException): Response =
    Response.status(403).header("Content-Type", "application/json").entity(Map("message" -> e.getMessage)).build()
}
