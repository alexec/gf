package games.app

import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.Provider
import javax.ws.rs.{Consumes, Produces}

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import com.fasterxml.jackson.module.scala.DefaultScalaModule

@Provider
@Consumes(Array(MediaType.APPLICATION_JSON))
@Produces(Array(MediaType.APPLICATION_JSON))
class ScalaFriendlyJacksonJsonProvider extends JacksonJsonProvider {
  override def _locateMapperViaProvider(`type`: Class[_], mediaType: MediaType): ObjectMapper =
    new ObjectMapper()
      .registerModule(DefaultScalaModule)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}
