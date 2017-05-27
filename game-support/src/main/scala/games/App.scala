package games

import java.util.Properties

import com.mongodb.{MongoClient, WriteConcern}
import games.app._
import org.glassfish.hk2.api.Factory
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@EnableAutoConfiguration
abstract class App extends ResourceConfig {
  protected val properties: Properties = {
    val p = new Properties()
    p.putAll(System.getProperties)
    System.getenv().forEach((k, v) => p.put(k.replaceAll("_", ".").toLowerCase, v))
    p
  }
  protected val writeConcern: WriteConcern = WriteConcern.valueOf(properties.getProperty("mongodb.write-concern", "JOURNALED"))
  protected val mongo: MongoClient = new MongoClient(properties.getProperty("mongodb.host"))


  def bind[T](obj: T, repo: Class[T]): ResourceConfig = {
    register(new AbstractBinder() {

      override protected def configure() {
        bindFactory(new SimpleFactory(obj)).to(repo)
      }
    }, 0)
  }

  register(classOf[IllegalArgumentExceptionMapper])
  register(classOf[NotEnoughFundsExceptionMapper])
  register(classOf[ScalaFriendlyJacksonJsonProvider])
  register(classOf[ServerErrorExceptionMapper])

  protected class SimpleFactory[T](t: T) extends Factory[T] {
    override def dispose(instance: T): Unit = {}

    override def provide(): T = t
  }

}


