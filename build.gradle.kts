plugins {
  alias(libs.plugins.kotlin)
  alias(libs.plugins.kapt)
  alias(libs.plugins.serialization)
  alias(libs.plugins.shadow)
}

val kotlinVersion = libs.plugins.kotlin.get().version
val velocityVersion = libs.velocityApi.get().version

group = "com.velocitypowered"
version = "$velocityVersion+$kotlinVersion"

repositories {
  mavenLocal()
  mavenCentral()

  maven("https://repo.velocitypowered.com/snapshots/")
}

dependencies {
  compileOnly(libs.velocityApi)
  kapt(libs.velocityApi)

  // Kotlin Stdlib
  implementation(libs.kotlinStdlibJdk8)
  implementation(libs.kotlinStdlibJdk7)

  // Kotlin Coroutines
  implementation(libs.kotlinxCoroutinesCore)
  implementation(libs.kotlinxCoroutinesCoreJvm)
  implementation(libs.kotlinxCoroutinesJdk8)

  // Kotlin Serialization
  implementation(libs.kotlinxSerializationCoreJvm)
  implementation(libs.kotlinxSerializationJsonJvm)
  implementation(libs.kotlinxSerializationCborJvm)

  // Kotlin
  implementation(libs.kotlinReflect)
  implementation(libs.atomicfuJvm)
  implementation(libs.kotlinxDatetimeJvm)

  // Adventure
  implementation(libs.adventureExtraKotlin)
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
