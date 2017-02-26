package games

import classicslot.app.ClassicSlotController
import classicslot.infra.ClassicSlotRepo
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.mongodb.{MongoClient, WriteConcern}
import core.app.ControllerExceptionHandler
import core.model.Money
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.web._
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.core.convert.converter.Converter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import roulette.app.RouletteController
import roulette.infra.RouletteRepo


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
  //,classOf[SessionAutoConfiguration]
)) class Config {

  @Bean def mappingJackson2HttpMessageConverter() =
    new MappingJackson2HttpMessageConverter((new ObjectMapper() with ScalaObjectMapper)
      .registerModule(DefaultScalaModule)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))

  @Bean def exceptionHandler = new ControllerExceptionHandler

  @Bean def stringToMoney() = new Converter[String, Money] {
    override def convert(source: String): Money = Money(BigDecimal(source))
  }

  @Bean def writeConcern(@Value("${spring.data.mongodb.write-concern:JOURNALED}") value: String) = WriteConcern.valueOf(value)

  @Bean def rouletteController(mongo: MongoClient, writeConcern: WriteConcern) =
    new RouletteController(new RouletteRepo(mongo, writeConcern))

  @Bean def classicSlotController(mongo: MongoClient, writeConcern: WriteConcern) =
    new ClassicSlotController(new ClassicSlotRepo(mongo, writeConcern))

}


