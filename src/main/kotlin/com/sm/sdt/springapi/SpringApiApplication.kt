package com.sm.sdt.springapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringApiApplication

fun main(args: Array<String>) {
	val context = runApplication<SpringApiApplication>(*args)
}
