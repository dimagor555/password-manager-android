import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.preview)
                api(compose.ui)
                api(compose.materialIconsExtended)

                implementation(projects.root)
                implementation(projects.uiCore)

                implementation(Libs.Koin.core)
                implementation(Libs.Koin.compose)

                implementation(Libs.KotlinX.coroutinesCore)
                implementation(Libs.KotlinX.coroutinesSwing)

                implementation(Libs.napier)

                implementation(Libs.Decompose.decompose)
                implementation(Libs.Decompose.extensionsCompose)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "ru.dimagor555.passwordmanager.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "password-manager-android"
        }
    }
}