plugins {
  alias(libs.plugins.kotlin)
  alias(libs.plugins.kapt)
  alias(libs.plugins.serialization)
  alias(libs.plugins.shadow)
  `java-library`
  `maven-publish`
}

val kotlinVersion = libs.versions.kotlin.get()
val velocityVersion = libs.versions.velocity.get()

group = "com.velocitypowered"
version = "$velocityVersion+kotlin.$kotlinVersion"

repositories {
  mavenLocal()
  mavenCentral()

  maven("https://repo.velocitypowered.com/snapshots/")
}

dependencies {
  compileOnly(libs.velocityApi)
  kapt(libs.velocityApi)

  // Kotlin Stdlib
  api(libs.kotlinStdlib)
  api(libs.kotlinStdlibJdk8)
  api(libs.kotlinStdlibJdk7)

  // Kotlin Coroutines
  api(libs.kotlinxCoroutinesCore)
  api(libs.kotlinxCoroutinesCoreJvm)
  api(libs.kotlinxCoroutinesJdk8)

  // Kotlin Serialization
  api(libs.kotlinxSerializationCoreJvm)
  api(libs.kotlinxSerializationJsonJvm)
  api(libs.kotlinxSerializationCborJvm)

  // Kotlin
  api(libs.kotlinReflect)
  api(libs.atomicfuJvm)
  api(libs.kotlinxDatetimeJvm)

  // Adventure
  api(libs.adventureExtraKotlin)
}

tasks {
  build {
    dependsOn(shadowJar)
  }

  shadowJar {
    archiveClassifier.set("")
  }

  processResources {
    inputs.property("version", project.version)
    filesMatching("velocity-plugin.json") {
      expand("version" to project.version)
    }
  }

}


publishing {
  repositories {
    maven("https://maven.pkg.github.com/eupedroosouza/velocity-language-kotlin") {
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }

  publications {
    create<MavenPublication>(project.name) {
      from(components["java"])

      groupId = "com.velocitypowered"
      artifactId = project.name
      version = project.version.toString()

      pom {
        name.set("velocity-language-kotlin")
        url.set("https://www.velocitypowered.com")
        licenses {
          license {
            name.set("MIT License")
            url.set("https://opensource.org/licenses/MIT")
          }
        }
        contributors {
          contributor {
            name.set("Velocity Contributors")
          }
        }
      }
    }
  }
}
