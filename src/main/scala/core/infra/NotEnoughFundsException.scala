package core.infra

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "not enough funds")
class NotEnoughFundsException extends RuntimeException {

}
