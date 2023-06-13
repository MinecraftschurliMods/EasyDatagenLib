pluginManagement {
    plugins {
        id("org.ec4j.editorconfig") version "0.0.3"
        id("net.minecraftforge.gradle") version "[6.0,6.2)"
        id("org.parchmentmc.librarian.forgegradle") version "1.+"
    }
    repositories {
        gradlePluginPortal()
        maven {
            name = "MinecraftForge"
            url = uri("https://maven.minecraftforge.net/")
        }
        maven {
            name = "ParchmentMC"
            url = uri("https://maven.parchmentmc.org")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "EasyDatagenLib"
