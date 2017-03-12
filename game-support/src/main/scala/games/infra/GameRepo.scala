package games.infra

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.mongodb.client.model.{Filters, UpdateOptions}
import com.mongodb.util.JSON
import com.mongodb.{MongoClient, WriteConcern}
import games.model.Wallet
import org.bson.Document
import org.bson.conversions.Bson


class GameRepo[G, S](mongo: MongoClient, gameName: String, factory: GameFactory[G, S], writeConcern: WriteConcern) {

  private val mapper = (new ObjectMapper() with ScalaObjectMapper)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .registerModule(DefaultScalaModule)
    .enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY)
  private val collection = mongo.getDatabase(gameName).getCollection("state")
    .withWriteConcern(writeConcern)
  private val filter: (String => Bson) = id => Filters.eq("_id", id)

  private val upsert = new UpdateOptions().upsert(true)

  def set(id: String, game: G): Unit = {
    val state = factory.toMaybeState(game)
    if (state.isDefined) {
      collection.replaceOne(filter(id), Document.parse(mapper.writeValueAsString(state.get)).append("_id", id), upsert)
    } else {
      delete(id)
    }
  }

  def delete(id: String): Unit = collection.deleteOne(filter(id))

  def get(id: String, wallet: Wallet)(implicit ev: Manifest[S]): G = {

    val cursor = collection.find(filter(id)).iterator()

    if (cursor.hasNext) {
      factory.toGame(wallet, Some(mapper.readValue(JSON.serialize(cursor.next()), ev.runtimeClass).asInstanceOf[S]))
    } else {
      factory.toGame(wallet, None)
    }
  }
}
