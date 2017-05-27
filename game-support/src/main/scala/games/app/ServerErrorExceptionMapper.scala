package games.app

import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}


@Provider class ServerErrorExceptionMapper extends ExceptionMapper[Exception] {
  override def toResponse(ex: Exception): Response =
    Response
      .status(500)
      .entity(ex.getMessage)
      .`type`("text/plain")
      .build
}