package games

import java.net.URI
import java.util.Properties

import com.mongodb.{MongoClient, WriteConcern}
import games.app.{IllegalArgumentExceptionMapper, NotEnoughFundsExceptionMapper, ScalaFriendlyJacksonJsonProvider}
import org.glassfish.hk2.api.Factory
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig

abstract class App extends ResourceConfig {
  def run(): Unit = {
    val server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:8080"), this)
    server.start()
    Thread.currentThread().join()
    server.shutdown()
  }

  protected val properties: Properties = new Properties()
  properties.putAll(System.getProperties)
  System.getenv().forEach((k, v) => properties.put(k.replaceAll("_", ".").toLowerCase, v))
  protected val writeConcern: WriteConcern = WriteConcern.valueOf(properties.getProperty("mongodb.write-concern", "JOURNALED"))
  protected val mongo: MongoClient = new MongoClient(properties.getProperty("mongodb.host"))

  protected class SimpleFactory[T](t: T) extends Factory[T] {
    override def dispose(instance: T): Unit = {}

    override def provide(): T = t
  }

  register(classOf[IllegalArgumentExceptionMapper])
  register(classOf[NotEnoughFundsExceptionMapper])
  register(classOf[ScalaFriendlyJacksonJsonProvider])

  def bind[T](obj: T, repo: Class[T]): ResourceConfig = {
    register(new AbstractBinder() {

      override protected def configure() {
        bindFactory(new SimpleFactory(obj)).to(repo)
      }
    }, 0)
  }
}


