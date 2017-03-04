package games.app

import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}

import core.infra.NotEnoughFundsException

@Provider
class NotEnoughFundsExceptionMapper extends ExceptionMapper[NotEnoughFundsException] {
  override def toResponse(e: NotEnoughFundsException): Response =
    Response.status(403).entity(Map("message" -> e.getMessage)).build()
}
