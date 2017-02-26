package core.app

import games.Config
import org.springframework.context.annotation.{Configuration, Import}

@Configuration
@Import(Array(classOf[Config]))
class TestConfig {


}
