package gf

import org.springframework.boot.SpringApplication

object App {
  def main(args: Array[String]) {
    new SpringApplication(classOf[Config])
      .run()
  }
}


