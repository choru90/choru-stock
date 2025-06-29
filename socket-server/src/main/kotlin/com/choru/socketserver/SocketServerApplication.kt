package com.choru.socketserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class SocketServerApplication

fun main(args: Array<String>) {
    runApplication<SocketServerApplication>(*args)
}
