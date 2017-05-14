package games.app

import javax.ws.rs.core.{MediaType, Response}
import javax.ws.rs.{GET, Path, Produces}

import scala.io.Source

@Path("/index.html")
@Produces(Array(MediaType.TEXT_HTML))
class IndexController {

  @GET def index(): Response = {
    Response.ok(Source.fromInputStream(classOf[IndexController].getResourceAsStream("/index.html")).mkString).build()
  }
}
