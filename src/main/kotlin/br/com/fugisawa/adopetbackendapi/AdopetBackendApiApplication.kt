package br.com.fugisawa.adopetbackendapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class AdopetBackendApiApplication

fun main(args: Array<String>) {
    runApplication<AdopetBackendApiApplication>(*args)
}
