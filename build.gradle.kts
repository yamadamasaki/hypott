plugins {
  kotlin("jvm") version "1.6.21"
  application
  signing
  `maven-publish`
}

group = "jp.co.metabolics"
version = "1.0.1"

val sonatypeUsername: String? by project
val sonatypePassword: String? by project

java {
  withJavadocJar()
  withSourcesJar()
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      artifactId = "hypott"
      from(components["java"])
      versionMapping {
        usage("java-api") {
          fromResolutionOf("runtimeClasspath")
        }
        usage("java-runtime") {
          fromResolutionResult()
        }
      }
      pom {
        name.set("hypott")
        description.set("Hypott is a small software testing library.")
        url.set("https://github.com/yamadamasaki/hypott")
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        developers {
          developer {
            id.set("yamadamasaki") // ToDo
            name.set("YAMADA Masaki")
            email.set("masaki@metabolics.co.jp")
          }
        }
        scm {
          connection.set("https://github.com/yamadamasaki/hypott.git")
          developerConnection.set("https://github.com/yamadamasaki/hypott.git")
          url.set("https://github.com/yamadamasaki/hypott")
        }
      }
    }
  }
  repositories {
    maven {
      val releaseRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
      val snapshotRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
      url = if (version.toString().endsWith("SNAPSHOT")) snapshotRepoUrl else releaseRepoUrl
      credentials {
        username = sonatypeUsername
        password = sonatypePassword
      }
    }
  }

  signing {
    sign(publishing.publications["mavenJava"])
  }
}

tasks.javadoc {
  if (JavaVersion.current().isJava9Compatible) {
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.+")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")
  implementation("com.github.javafaker:javafaker:1.0.2")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
  }
}

application {
  mainClass.set("MainKt")
}
