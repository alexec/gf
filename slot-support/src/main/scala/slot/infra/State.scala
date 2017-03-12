package slot.infra

import slot.model.Stops

import scala.beans.BeanProperty

case class State(@BeanProperty stops: Stops)
