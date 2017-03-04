package core.infra

import javax.ws.rs.ext.Provider


@Provider
class NotEnoughFundsException(message: String) extends RuntimeException(message)
