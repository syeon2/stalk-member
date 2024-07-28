package io.waterkite94.stalk.usecase

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application {
    fun main(args: Array<String>) {
        SpringApplication.run(Application::class.java, *args)
    }
}