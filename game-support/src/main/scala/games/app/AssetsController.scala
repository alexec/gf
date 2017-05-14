package games.app

import javax.ws.rs.core.Response
import javax.ws.rs.{GET, Path, PathParam}

import scala.io.Source


@Path("/assets")
class AssetsController {
  private val contentTypes = Map(
    "js" -> "application/javascript",
    "png" -> "image/png"
  )

  @GET
  @Path("{path:.*}.{ext:.*}")
  def get(@PathParam("path") path: String, @PathParam("ext") ext: String): Response = {
    val resource = ("/assets/" + path + "." + ext).replaceAllLiterally("..", "")
    Response
      .ok(Source.fromInputStream(classOf[AssetsController].getResourceAsStream(resource)).mkString)
      .header("Content-Type", contentTypes(ext))
      .build()
  }
}
