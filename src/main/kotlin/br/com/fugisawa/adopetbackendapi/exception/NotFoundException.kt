package br.com.fugisawa.adopetbackendapi.exception

import java.lang.RuntimeException

class NotFoundException(message: String?) : RuntimeException(message) {
}