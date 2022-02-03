buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Plugins.GRADLE)
        classpath(Dependencies.Plugins.KOTLIN)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}