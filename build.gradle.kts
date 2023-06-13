import groovy.util.Node
import groovy.util.NodeList
import java.time.Instant
import java.time.format.DateTimeFormatter

plugins {
    idea
    eclipse
    `maven-publish`
    id("org.ec4j.editorconfig")
    id("net.minecraftforge.gradle")
    id("org.parchmentmc.librarian.forgegradle")
}
//=============================================

val lib_group: String by project
val lib_name: String by project
val lib_version: String by project
val lib_vendor: String by project
val github: String by project
val url: String by project
val vendor: String by project
val java_version: String by project
val mappings_channel: String by project
val mappings_version: String by project
val mc_version: String by project
val forge_version: String by project

val SourceSetContainer.api: NamedDomainObjectProvider<SourceSet>
    get() = named("api")

val Provider<SourceSet>.output: Provider<SourceSetOutput>
    get() = map { it.output }

val Provider<SourceSet>.allSource: Provider<SourceDirectorySet>
    get() = map { it.allSource }

val Provider<SourceSet>.compileClasspath: Provider<FileCollection>
    get() = map { it.compileClasspath }

editorconfig {
    excludes = listOf("**/run/**", "**/out/**", "**/.idea/**", "**/gradlew*")
    isExcludeNonSourceFiles = true
}

group = lib_group
version = "${mc_version}-${lib_version}"
base.archivesName.set(lib_name)


if (System.getenv("RELEASE_TYPE") != null) {
    status = System.getenv("RELEASE_TYPE").lowercase()
    if (status == "snapshot") status = (status as String).uppercase()
    version = "${version}-${status}"
}

java {
    withSourcesJar()
    withJavadocJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(java_version))
        if (System.getenv("GITHUB_ACTIONS") == null || System.getenv("GITHUB_ACTIONS").isEmpty()) {
            vendor.set(JvmVendorSpec.matching("JetBrains s.r.o."))
        } else {
            vendor.set(JvmVendorSpec.ADOPTOPENJDK)
        }
    }
}

minecraft {
    mappings(mappings_channel, mappings_version)
}

sourceSets {
    main
    create("api")
}

configurations {
    getByName("apiCompileClasspath").extendsFrom(minecraft.get())
    create("deobfJar") {
        isCanBeConsumed = true
        isCanBeResolved = false
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "Minecraftschurli Maven"
        url = uri("https://minecraftschurli.ddns.net/repository/maven-public")
    }
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "${mc_version}-${forge_version}")
    compileOnly("org.jetbrains:annotations:22.0.0")
    implementation(sourceSets.api.output)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).tags = listOf(
        "side:a:Side:",
        "apiNote:a:API Note:",
        "implSpec:a:Implementation Requirements:",
        "implNote:a:Implementation Note:"
    )
    if(JavaVersion.current().isJava9Compatible) {
        (options as CoreJavadocOptions).addBooleanOption("html5", true)
    }
}

tasks {
    jar {
        from(sourceSets.main.output)
        from(sourceSets.api.output)
        finalizedBy("reobfJar")
    }
    named<Jar>("sourcesJar") {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(sourceSets.main.allSource)
        from(sourceSets.api.allSource)
    }
    register<Jar>("apiJar") {
        dependsOn(named("apiClasses"))
        archiveClassifier.set("api")
        from(sourceSets.api.allSource)
        from(sourceSets.api.output)
        finalizedBy("reobfJar")
    }
    register("reobf") {
        dependsOn(named("reobfJar"))
        dependsOn(named("reobfApiJar"))
    }
    register<Jar>("deobfJar") {
        dependsOn(classes)
        archiveClassifier.set("deobf")
        from(sourceSets.main.output)
        from(sourceSets.api.output)
    }
    withType<Jar>().configureEach {
        from("LICENSE")
        manifest {
            attributes(mapOf(
                "Specification-Title"    to base.archivesName.get(),
                "Specification-Vendor"   to lib_vendor,
                "Specification-Version"  to "1",
                "Implementation-Title"   to base.archivesName.get(),
                "Implementation-Version" to lib_version,
                "Implementation-Vendor"  to lib_vendor,
                "Built-On-Java"          to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                "Built-On"               to "${mc_version}-${forge_version}",
                "Timestamp"              to DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                "FMLModType"             to "GAMELIBRARY",
                "LICENSE"                to "MIT"
            ))
        }
    }
    withType<GenerateModuleMetadata>().configureEach {
        enabled = false
    }
    publish {
        dependsOn(check)
    }
}

reobf {
    create("apiJar") { classpath.from(sourceSets.api.compileClasspath) }
    create("jar") { classpath.from(sourceSets.main.compileClasspath) }
}

artifacts {
    archives(tasks.named("jar"))
    archives(tasks.named("apiJar"))
    archives(tasks.named("deobfJar"))
    archives(tasks.named("sourcesJar"))
    archives(tasks.named("javadocJar"))
}

publishing {
    publications.create<MavenPublication>("${base.archivesName.get()}ToMaven") {
        artifact(tasks.named("apiJar"))
        artifact(tasks.named("deobfJar"))
        groupId = project.group as String
        artifactId = base.archivesName.get()
        version = project.version as String
        from(components["java"])
        pom {
            name.set(name)
            url.set(url)
            packaging = "jar"
            scm {
                connection.set("scm:git:git://github.com/${github}.git")
                developerConnection.set("scm:git:git@github.com:${github}.git")
                url.set("https://github.com/${github}")
            }
            issueManagement {
                system.set("github")
                url.set("https://github.com/${github}.git/issues")
            }
            organization {
                name.set("Minecraftschurli Mods")
                url.set("https://github.com/Minecraftschurli")
            }
            developers {
                developer {
                    id.set("minecraftschurli")
                    name.set("Minecraftschurli")
                    email.set("minecraftschurli@gmail.com")
                    organization.set("Minecraftschurli Mods")
                    organizationUrl.set("https://github.com/Minecraftschurli")
                    timezone.set("Europe/Vienna")
                }
            }
            licenses {
                license {
                    name.set("MIT")
                    url.set("https://github.com/${github}/blob/main/LICENSE")
                    distribution.set("repo")
                }
            }
            withXml {
                val rootNode = asNode()
                val dependencies: NodeList = (rootNode.get("dependencies") as NodeList)
                val dependencyList = dependencies.getAt("dependency")
                for (dependency in dependencyList) {
                    val dependencyNode = dependency as Node
                    val version = ((((dependencyNode.get("version") as NodeList).last() as Node).value() as NodeList).last() as String)
                    if (version.contains("_mapped_")) {
                        assert(dependencyNode.parent().remove(dependencyNode))
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val env = System.getenv()
            if (env.containsKey("MAVEN_USER") && env.containsKey("MAVEN_PASSWORD") && env.containsKey("MAVEN_URL")) {
                url = uri(env["MAVEN_URL"] as String)
                credentials {
                    username = env["MAVEN_USER"]
                    password = env["MAVEN_PASSWORD"]
                }
            } else {
                url = uri("$buildDir/repo")
            }
        }
    }
}
