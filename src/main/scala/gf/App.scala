package gf

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.mongodb.MongoClient
import gf.app.roulette.RouletteController
import gf.infra.roulette.RouletteRepo
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.web._
import org.springframework.context.annotation.{Bean, Configuration, Import}

object App {
  def main(args: Array[String]) {
    new SpringApplication(classOf[App])
      .run()
  }
}

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
)) class App {
  @Bean def objectMapper(): ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)

  @Bean def rouletteRepo(mongo: MongoClient): RouletteRepo = new RouletteRepo(mongo)

  @Bean def rouletteController(repo: RouletteRepo): RouletteController = new RouletteController(repo)
}


