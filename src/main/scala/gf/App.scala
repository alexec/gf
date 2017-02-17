package gf

import gf.app.roulette.RouletteController
import gf.infra.RouletteRepo
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
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
  classOf[WebMvcAutoConfiguration]
)) class App {
  @Bean def gameRepo = new RouletteRepo

  @Bean def playController(rouletteRepo: RouletteRepo) = new RouletteController(rouletteRepo)
}


