buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Plugins.GRADLE)
        classpath(Dependencies.Plugins.KOTLIN)
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}