buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.androidBuildTools)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.hiltAndroid)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}