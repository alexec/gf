package gf.infra.slot.classic

import gf.model.slot.Stops

import scala.beans.BeanProperty

case class State(@BeanProperty stops: Stops)
