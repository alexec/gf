package games.app

import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}

@Provider
class IllegalArgumentExceptionMapper extends ExceptionMapper[IllegalArgumentException]{
  override def toResponse(e: IllegalArgumentException): Response =
    Response.status(400).header("Content-Type", "application/json").entity(Map("message" -> e.getMessage)).build()
}
