package games.app

import javax.ws.rs.core.{MediaType, Response}
import javax.ws.rs.{GET, Path, PathParam, Produces}

@Path("/games")
@Produces(Array(MediaType.TEXT_HTML))
class IndexController {

  @Path("/{gameName}")
  @GET
  def index(@PathParam("gameName") gameName: String): Response = {
    val assets =
      List(
        "/assets/core/js/jquery-3.1.1.min.js",
        "/assets/core/js/core.js",
        s"/assets/games/$gameName/js/game.js"
      )

    val scripts = assets.map(asset => s"<script src='$asset'></script>").mkString("\n")
    Response
      .ok(
        s"""<html>
          <head>
           <meta charset="utf-8" />
              $scripts
          </head>
            <body>
            <div id="balance"></div>
            <div id="canvas"></div>
            <div id="buttons"></div>
            <script defer>
              setTimeout(function() {game.init(\"canvas\", 800, 600);}, 250);
            </script>
            </body>
            </html>""")
      .build()
  }
}
