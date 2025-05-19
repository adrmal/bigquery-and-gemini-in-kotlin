plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.github.adrmal"
version = "1.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	/**
	 * These libraries should have consistent versions.
	 * Otherwise, you can get error while running your code.
	 * The best way is to find the latest version of com.google.cloud:libraries-bom and read versions of VertexAI and BigQuery from that BOM
	 * (for example here: https://github.com/googleapis/java-cloud-bom).
	 */
	implementation("com.google.cloud:google-cloud-vertexai:1.22.0")
	implementation("com.google.cloud:google-cloud-bigquery:2.50.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
