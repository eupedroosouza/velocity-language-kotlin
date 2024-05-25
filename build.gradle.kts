plugins {
  alias(libs.plugins.kotlin)
  alias(libs.plugins.kapt)
  alias(libs.plugins.serialization)
  alias(libs.plugins.shadow)
  `java-library`
}

val kotlinVersion = libs.plugins.kotlin.get().version
val velocityVersion = libs.velocityApi.get().version

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
