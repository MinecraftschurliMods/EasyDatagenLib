plugins {
    id ("com.github.minecraftschurlimods.helperplugin")
}

sourceSets.register("api")

val neo by configurations.creating
configurations.implementation.configure { extendsFrom(neo) }
configurations.named("apiCompileOnly").configure { extendsFrom(neo) }

dependencies {
    neo(helper.neoforge())
    implementation(sourceSets.api.output)
    compileOnly("org.jetbrains:annotations:23.0.0")
    "apiCompileOnly"("org.jetbrains:annotations:23.0.0")
}

tasks.jar {
    from(sourceSets.main.output)
    from(sourceSets.api.output)
}

tasks.named<Jar>("sourcesJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.allSource)
    from(sourceSets.api.allSource)
}

val apiJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(tasks.named("apiClasses"))
    archiveClassifier.set("api")
    from(sourceSets.api.allSource)
    from(sourceSets.api.output)
}

artifacts.archives(apiJar)

helper.publication.apply {
    artifact(apiJar)
    pom {
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
}

val SourceSetContainer.api: Provider<SourceSet> get() = named("api")
val Provider<SourceSet>.allSource: Provider<SourceDirectorySet> get() = map { it.allSource }
val Provider<SourceSet>.output: Provider<SourceSetOutput> get() = map { it.output }
