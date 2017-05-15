package games

import java.net.URI
import java.util.Properties

import com.mongodb.{MongoClient, WriteConcern}
import games.app._
import org.glassfish.hk2.api.Factory
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig

abstract class App extends ResourceConfig {
  protected val properties: Properties = new Properties()
  protected val writeConcern: WriteConcern = WriteConcern.valueOf(properties.getProperty("mongodb.write-concern", "JOURNALED"))
  properties.putAll(System.getProperties)
  System.getenv().forEach((k, v) => properties.put(k.replaceAll("_", ".").toLowerCase, v))
  protected val mongo: MongoClient = new MongoClient(properties.getProperty("mongodb.host"))

  def run(): Unit = {
    val server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:8080"), this)
    server.start()
    Thread.currentThread().join()
    server.shutdown()
  }

  def bind[T](obj: T, repo: Class[T]): ResourceConfig = {
    register(new AbstractBinder() {

      override protected def configure() {
        bindFactory(new SimpleFactory(obj)).to(repo)
      }
    }, 0)
  }

  register(classOf[IndexController])
  register(classOf[AssetsController])

  register(classOf[IllegalArgumentExceptionMapper])
  register(classOf[NotEnoughFundsExceptionMapper])
  register(classOf[ScalaFriendlyJacksonJsonProvider])
  register(classOf[ServerErrorExceptionMapper])

  protected class SimpleFactory[T](t: T) extends Factory[T] {
    override def dispose(instance: T): Unit = {}

    override def provide(): T = t
  }
}


