package com.github.adrmal.bigquery_and_gemini

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ApplicationRunner {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(ApplicationRunner::class.java, *args)
		}
	}
}
