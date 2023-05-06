plugins {
  kotlin("jvm") version "1.8.21"
  application
}

group = "jp.co.metabolics"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(11)
}

application {
  mainClass.set("MainKt")
}
