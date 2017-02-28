package games

import java.net.URI
import java.util.Properties

import classicslot.app.ClassicSlotController
import classicslot.infra.ClassicSlotRepo
import com.mongodb.{MongoClient, WriteConcern}
import org.glassfish.hk2.api.Factory
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import roulette.app.RouletteController
import roulette.infra.RouletteRepo

object App {
  def main(args: Array[String]): Unit = {
    val server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:8080"), new App)
    server.start()
    Thread.currentThread().join()
    server.shutdown()
  }
}

class App extends ResourceConfig {
  private val properties = new Properties()
  private val writeConcern: WriteConcern = WriteConcern.valueOf(properties.getProperty("mongodb.write-concern", "JOURNALED"))
  private val mongo = new MongoClient()
  private val rouletteRepo = new RouletteRepo(mongo, writeConcern)
  private val classicSlotRepo = new ClassicSlotRepo(mongo, writeConcern)

  class SimpleFactory[T](t: T) extends Factory[T] {
    override def dispose(instance: T): Unit = {}

    override def provide(): T = t
  }

  register(classOf[RouletteController])
  register(classOf[ClassicSlotController])

  register(new AbstractBinder() {
    override protected def configure() {
      bindFactory(new SimpleFactory(rouletteRepo)).to(classOf[RouletteRepo])
      bindFactory(new SimpleFactory(classicSlotRepo)).to(classOf[ClassicSlotRepo])
      //bindFactory(new SimpleFactory(Money(1))).to(classOf[Money])
    }
  }, 0)
}


