package com.testcode.kiosk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class TestCodeApplication

fun main(args: Array<String>) {
    runApplication<TestCodeApplication>(*args)
}
