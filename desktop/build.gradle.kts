import org.jetbrains.compose.desktop.application.dsl.TargetFormat

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
                implementation(projects.encryption.symmetric)
                implementation(projects.encryption.asymmetric)
                implementation(projects.passwordFeature)
                implementation(projects.passwordGenerationFeature)
                implementation(projects.masterPasswordFeature)
                implementation(projects.exportFeature.impl)
                implementation(projects.exportFeature.passwordIntegration)
                implementation(projects.root)
                implementation(projects.uiCore)
                implementation(projects.synchronizationFeature)
                implementation(projects.syncPasswordIntegration)

                implementation(compose.desktop.currentOs)
                implementation(compose.runtime)
                implementation(compose.foundation)

                implementation(Libs.Koin.core)

                implementation(Libs.KotlinX.coroutinesCore)
                implementation(Libs.KotlinX.coroutinesSwing)

                implementation(Libs.Decompose.decompose)
                implementation(Libs.Decompose.extensionsCompose)

                implementation(Libs.Ktor.serverCore)
                implementation(Libs.Ktor.serverCio)
                implementation(Libs.Ktor.serverContentNegotiation)

                implementation(Libs.napier)
            }
        }
    }
}

configurations.commonMainApi {
    exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "password-manager-android"
        }
    }
}