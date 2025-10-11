package com.sm.sdt.springapi

import com.sm.sdt.springapi.config.JwtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringApiApplication

fun main(args: Array<String>) {
	runApplication<SpringApiApplication>(*args)
}
