package com.choru.socketserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Tick 데이터를 주기적으로 수집하여 브로드캐스트하기 위해 스케줄링을 활성화한다.
 */
@EnableScheduling
@SpringBootApplication
class SocketServerApplication

fun main(args: Array<String>) {
    runApplication<SocketServerApplication>(*args)
}
