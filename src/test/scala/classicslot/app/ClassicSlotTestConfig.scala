package classicslot.app

import classicslot.infra.ClassicSlotRepo
import core.app.TestConfig
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{DeleteMapping, RequestHeader, ResponseStatus, RestController}

@Configuration
@Import(Array(classOf[TestConfig]))
class ClassicSlotTestConfig {

  @Bean
  def deleteController(repo: ClassicSlotRepo) = new DeleteController(repo)

}

@RestController
class DeleteController(repo: ClassicSlotRepo) {
  @DeleteMapping(Array("/games/classic-slot"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(@RequestHeader("PlayerId") playerId: String): Unit = repo.delete(playerId)
}
