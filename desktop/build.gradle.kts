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
                api(compose.desktop.currentOs)

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.preview)
                api(compose.ui)
                api(compose.materialIconsExtended)

                implementation(projects.root)
                implementation(projects.uiCore)
                implementation(projects.synchronizationFeature)
                implementation(projects.syncPasswordIntegration)

                implementation(Libs.Koin.core)
                implementation(Libs.Koin.compose)

                implementation(Libs.KotlinX.coroutinesCore)
                implementation(Libs.KotlinX.coroutinesSwing)
                implementation(Libs.KotlinX.coroutinesTest)

                implementation(Libs.napier)

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
        mainClass = "ru.dimagor555.passwordmanager.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "password-manager-android"
        }
    }
}