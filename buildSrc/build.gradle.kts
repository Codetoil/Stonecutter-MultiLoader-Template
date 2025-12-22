plugins {
	`kotlin-dsl`
	kotlin("jvm") version "2.3.0"
}

repositories {
	mavenCentral()
	gradlePluginPortal()
	maven("https://maven.kikugie.dev/snapshots")
}

dependencies {
	implementation("dev.kikugie:stonecutter:0.8")
}