package games.app

import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}

@Provider
class GeneralExceptionMapper extends ExceptionMapper[Exception]{
  override def toResponse(e: Exception): Response =
    Response.serverError().entity(Map("message" -> e.getMessage)).build()
}
