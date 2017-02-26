package roulette.app

import core.app.TestConfig
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{DeleteMapping, RequestHeader, ResponseStatus, RestController}
import roulette.infra.RouletteRepo

@Configuration
@Import(Array(classOf[TestConfig]))
class RouletteControllerTestConfig {

  @Bean
  def deleteController(repo: RouletteRepo) = new DeleteController(repo)

}

@RestController
class DeleteController(repo: RouletteRepo) {
  @DeleteMapping(Array("/games/roulette"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(@RequestHeader("PlayerId") playerId: String): Unit = repo.delete(playerId)
}
