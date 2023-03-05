plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
}

kotlin {
    android()
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
                implementation(projects.passwordFeature)
                implementation(projects.passwordGenerationFeature)
                implementation(projects.masterPasswordFeature)

                implementation(compose.runtime)
                implementation(compose.foundation)

                implementation(Libs.Koin.core)

                implementation(Libs.MviCompose.core)
                implementation(Libs.Decompose.decompose)
                implementation(Libs.Decompose.extensionsCompose)

                implementation(Libs.napier)
            }
        }
    }
}

android {
    compileSdk = Android.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/licenses/*")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}
