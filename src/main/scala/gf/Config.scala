package gf

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.mongodb.MongoClient
import gf.app.roulette.RouletteController
import gf.infra.roulette.RouletteRepo
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.web._
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

object Config {
  def main(args: Array[String]) {
    new SpringApplication(classOf[Config])
      .run()
  }
}

//noinspection TypeAnnotation
@Configuration
@Import(Array(
  classOf[DispatcherServletAutoConfiguration],
  classOf[EmbeddedServletContainerAutoConfiguration],
  classOf[ErrorMvcAutoConfiguration],
  classOf[HttpEncodingAutoConfiguration],
  classOf[HttpMessageConvertersAutoConfiguration],
  classOf[JacksonAutoConfiguration],
  classOf[ServerPropertiesAutoConfiguration],
  classOf[PropertyPlaceholderAutoConfiguration],
  classOf[WebMvcAutoConfiguration],
  classOf[MongoAutoConfiguration]
)) class Config {

  @Bean def mappingJackson2HttpMessageConverter() =
    new MappingJackson2HttpMessageConverter((new ObjectMapper() with ScalaObjectMapper)
      .registerModule(DefaultScalaModule)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))

  @Bean def rouletteRepo(mongo: MongoClient) = new RouletteRepo(mongo)

  @Bean def rouletteController(repo: RouletteRepo) = new RouletteController(repo)
}


