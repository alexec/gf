package gf.infra

import java.security.SecureRandom

import gf.model.core.NullWallet
import gf.model.roulette.Roulette
import org.springframework.stereotype.Repository

@Repository
class RouletteRepo {
  def put(roulette: Roulette): Unit = {}

  def get(): Roulette = {
    new Roulette(Roulette.randomPocket(new SecureRandom()), NullWallet)
  }
}
