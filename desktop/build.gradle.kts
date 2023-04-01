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
                implementation(projects.encryptionCore)
                implementation(projects.passwordFeature)
                implementation(projects.passwordGenerationFeature)
                implementation(projects.masterPasswordFeature)
                implementation(projects.root)
                implementation(projects.uiCore)
                implementation(projects.synchronizationFeature)
                implementation(projects.syncPasswordIntegration)

                implementation(compose.desktop.currentOs)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.preview)
                implementation(compose.ui)
                implementation(compose.materialIconsExtended)

                implementation(Libs.Koin.core)
                implementation(Libs.Koin.compose)

                implementation(Libs.KotlinX.coroutinesCore)
                implementation(Libs.KotlinX.coroutinesSwing)
                implementation(Libs.KotlinX.coroutinesTest)

                implementation(Libs.Decompose.decompose)
                implementation(Libs.Decompose.extensionsCompose)

                implementation(Libs.Ktor.clientCore)
                implementation(Libs.Ktor.clientCio)

                implementation(Libs.Ktor.serverCore)
                implementation(Libs.Ktor.serverCio)
                implementation(Libs.Ktor.serverContentNegotiation)
            }
        }
    }
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