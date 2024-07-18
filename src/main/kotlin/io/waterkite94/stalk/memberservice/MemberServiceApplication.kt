package io.waterkite94.stalk.memberservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemberServiceApplication

fun main(args: Array<String>) {
    runApplication<MemberServiceApplication>(*args)
}
