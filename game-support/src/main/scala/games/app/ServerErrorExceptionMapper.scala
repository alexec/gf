package games.app

import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}

import org.glassfish.grizzly.utils.Exceptions

@Provider class ServerErrorExceptionMapper extends ExceptionMapper[Exception] {
  override def toResponse(ex: Exception): Response =
    Response
      .status(500)
      .entity(Exceptions.getStackTraceAsString(ex))
      .`type`("text/plain")
      .build
}