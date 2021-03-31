plugins {
    val kotlinVersion = "1.4.30"
    kotlin("jvm") version kotlinVersion

    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "io.github.sunshinewzy"
version = "1.0.0"

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://dl.bintray.com/karlatemp/misc") }
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
    maven {
        url = uri("http://maven.imagej.net/content/groups/public")
        name = "imagej.public"
    }
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.6.0")

    compileOnly(fileTree(mapOf("dir" to "cores", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    
    api("net.mamoe", "mirai-core", "2.4.0")

    runtimeOnly("net.mamoe:mirai-login-solver-selenium:1.0-dev-16")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    
    jar {
        archiveBaseName.set("SunnyAurora")
        archiveVersion.set(project.version.toString())
        destinationDirectory.set(file("F:/Java/Debug/Spigot-1.12/plugins"))
    }

    shadowJar {
        archiveBaseName.set("SunnyAurora")
        archiveVersion.set(project.version.toString())
        destinationDirectory.set(file("F:/Java/Debug/Spigot-1.12/plugins"))
        
        dependencies { 
            exclude(dependency(kotlinLib("kotlin-stdlib", "1.4.21")))
            exclude(dependency(kotlinLib("kotlin-stdlib", "1.4.30")))
            exclude(dependency(kotlinLib("kotlin-stdlib-common", "1.4.21")))
            exclude(dependency(kotlinLib("kotlin-stdlib-common", "1.4.30")))
            exclude(dependency(kotlinLib("kotlin-stdlib-jdk7", "1.4.21")))
            exclude(dependency(kotlinLib("kotlin-stdlib-jdk7", "1.4.30")))
            exclude(dependency(kotlinLib("kotlin-stdlib-jdk8", "1.4.21")))
            exclude(dependency(kotlinLib("kotlin-stdlib-jdk8", "1.4.30")))
            exclude(dependency(kotlinLib("kotlin-reflect", "1.4.21")))
        }
    }
}

fun kotlinLib(name: String, version: String): String = "org.jetbrains.kotlin:$name:$version"
