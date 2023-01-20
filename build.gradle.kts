plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false

    id("com.android.application") apply false
    id("com.android.library") apply false

    id("org.jetbrains.compose") version "1.2.0" apply false

    id("dev.icerock.mobile.multiplatform-resources") version "0.20.1" apply false

    id("io.realm.kotlin") version "1.5.2" apply false
}