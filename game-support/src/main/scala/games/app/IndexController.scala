package games.app

import javax.ws.rs.core.{MediaType, Response}
import javax.ws.rs.{GET, Path, Produces}

import scala.io.Source

@Path("/")
@Produces(Array(MediaType.TEXT_HTML))
class IndexController {

  @GET def index(): Response = {
    val assets = Source.fromInputStream(classOf[IndexController].getResourceAsStream("/manifest")).getLines()
    val scripts = assets.map(asset => s"<script src='$asset'></script>").mkString("\n")
    Response
      .ok(
        s"""<html>
          <head>
              <script>
                  core = {
                      canvas: {
                          getWidth: function () {
                              return 800
                          },
                          getHeight: function () {
                              return 600
                          }
                      },
                      coin: 0.01,
                      init: function () {
                      },
                      ready: function () {
                      },
                      unready: function () {
                      },
                      api: function (path) {
                          return "/api" + path;
                      },
                      setBalance: function (balance) {
                          document.getElementById("balance").innerHTML = balance;
                      },
                      handleError: function (x, t, r) {
                          alert(r)
                      },
                      addButton: function (text, fn) {
                          var b = document.createElement("div");
                          b.innerHTML = text;
                          b.onclick = fn;
                          document.getElementById("buttons").appendChild(b);
                      },
                      enableButton: function () {
                      },
                      disableButton: function () {
                      }
                  }
              </script>
              $scripts
              </head>
              <body>
            <div id="balance"></div>
            <div id="canvas"></div>
            <div id="buttons"></div>
            </body>
            </html>""")
      .build()
  }
}
