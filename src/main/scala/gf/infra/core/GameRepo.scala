package gf.infra.core

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.mongodb.util.JSON
import com.mongodb.{BasicDBObject, MongoClient}
import gf.model.core.Wallet
import org.bson.Document
import org.springframework.stereotype.Repository


@Repository
class GameRepo[G, S](mongo: MongoClient, gameName: String, factory: GameFactory[G, S]) {

  private val mapper = (new ObjectMapper() with ScalaObjectMapper)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .registerModule(DefaultScalaModule)
    .enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY)
  private val collection = mongo.getDatabase(gameName).getCollection("state")

  def set(game: G): Unit = {
    val state = factory.toMaybeState(game)
    if (state.isDefined) {
      val json = mapper.writeValueAsString(state.get)
      collection.insertOne(Document.parse(json))
    }
    else
      delete()
  }

  def delete(): Unit = collection.deleteOne(new BasicDBObject())

  def get(wallet: Wallet)(implicit ev: Manifest[S]): G = {

    val cursor = collection.find().iterator()

    if (cursor.hasNext) {
      factory.toGame(wallet, Some(mapper.readValue(JSON.serialize(cursor.next()), ev.runtimeClass).asInstanceOf[S]))
    } else {
      factory.toGame(wallet, None)
    }
  }
}
