plugins {
    id("org.jetbrains.kotlin.jvm")
    id("jacoco")
}

group = "com.github.shiraji"
version = "1.0.0"

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
    maxHeapSize = "3g"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:1.3.31")
}

tasks.register("resolveDependencies") {
    doLast {
        project.rootProject.allprojects.forEach {subProject ->
            subProject.buildscript.configurations.forEach {configuration ->
                if (configuration.isCanBeResolved) {
                    configuration.resolve()
                }
            }
            subProject.configurations.forEach {configuration ->
                if (configuration.isCanBeResolved) {
                    configuration.resolve()
                }
            }
        }
    }
}

inline operator fun <T : Task> T.invoke(a: T.() -> Unit): T = apply(a)
