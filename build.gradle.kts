plugins {
    idea
    id("net.neoforged.gradle.userdev")
    id("com.github.minecraftschurlimods.helperplugin")
}

helper.withApiSourceSet()

repositories {
    maven {
        name = "NeoForge Maven for PR #2993" // https://github.com/neoforged/NeoForge/pull/2993
        url = uri("https://prmaven.neoforged.net/NeoForge/pr2993")
        content {
            includeModule("net.neoforged", "neoforge")
            includeModule("net.neoforged", "testframework")
        }
    }
}

dependencies {
    implementation(helper.neoforge())
    compileOnly("org.jetbrains:annotations:23.0.0")
    "apiCompileOnly"("org.jetbrains:annotations:23.0.0")
}

minecraft.accessTransformers.file("src/main/resources/META-INF/accesstransformer.cfg")

helper.publication.pom {
    organization {
        name = "Minecraftschurli Mods"
        url = "https://github.com/MinecraftschurliMods"
    }
    developers {
        developer {
            id = "minecraftschurli"
            name = "Minecraftschurli"
            email = "minecraftschurli@gmail.com"
            organization = "Minecraftschurli Mods"
            organizationUrl = "https://github.com/Minecraftschurli"
            timezone = "Europe/Vienna"
        }
    }
}
