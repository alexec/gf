package core.app

import javax.servlet.http.HttpServletResponse

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler}

@ControllerAdvice
class ControllerExceptionHandler {

  @ExceptionHandler(Array(classOf[IllegalArgumentException]))
  def handleIllegalArgumentException(e: IllegalArgumentException, response: HttpServletResponse) = {
    response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage)
  }
}