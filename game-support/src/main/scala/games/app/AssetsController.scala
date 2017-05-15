package games.app

import java.io.BufferedInputStream
import javax.ws.rs.core.Response
import javax.ws.rs.{GET, Path, PathParam}

import scala.language.postfixOps


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
    val in = classOf[AssetsController].getResourceAsStream(resource)
    if (in == null) {
      return Response.status(404).build()
    }

    val bis = new BufferedInputStream(in)
    val arr = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray

    Response
      .ok(arr)
      .header("Content-Type", contentTypes(ext))
      .build()
  }
}
