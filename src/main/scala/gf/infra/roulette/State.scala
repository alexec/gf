package gf.infra.roulette

import gf.model.roulette.{Bet, Pocket}

import scala.beans.BeanProperty

case class State(@BeanProperty pocket: Pocket, @BeanProperty bets: List[Bet])
