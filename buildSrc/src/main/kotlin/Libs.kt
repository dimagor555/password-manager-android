object Libs {
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

    const val napier = "io.github.aakira:napier:2.6.1"

    const val apacheCodec = "commons-codec:commons-codec:1.15"

    object Compose {
        const val activity = "androidx.activity:activity-compose:1.7.0-alpha02"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.9.0"

        const val appCompat = "androidx.appcompat:appcompat:1.6.0"

        const val splashScreen = "androidx.core:core-splashscreen:1.0.0"

        const val securityCrypto = "androidx.security:security-crypto:1.1.0-alpha04"
        const val biometric = "androidx.biometric:biometric:1.2.0-alpha05"
    }

    object KotlinX {
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.0"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
        const val coroutinesSwing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    }

    object MviCompose {
        private const val version = "1.0.0"
        const val core = "io.github.dimagor555.mvicompose:core:$version"
        const val android = "io.github.dimagor555.mvicompose:android:$version"
    }

    object Room {
        private const val roomVersion = "2.5.0-beta01"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
    }

    object Realm {
        private const val realmVersion = "1.5.2"
        const val base = "io.realm.kotlin:library-base:$realmVersion"
    }

    object Koin {
        private const val version = "3.3.2"
        private const val composeVersion = "3.4.1"
        const val core = "io.insert-koin:koin-core:$version"
        const val android = "io.insert-koin:koin-android:$version"
        const val compose = "io.insert-koin:koin-androidx-compose:$composeVersion"
    }

    object MokoResources {
        private const val version = "0.20.1"
        const val commonMain = "dev.icerock.moko:resources:$version"
        const val androidMain = "dev.icerock.moko:resources-compose:$version"
        const val jvmMain = "dev.icerock.moko:resources-compose:$version"
    }

    object Decompose {
        private const val version = "1.0.0-beta-04"
        const val decompose = "com.arkivanov.decompose:decompose:$version"
        const val extensionsCompose = "com.arkivanov.decompose:extensions-compose-jetbrains:$version"
    }

    object Argon2 {
        const val android = "com.lambdapioneer.argon2kt:argon2kt:1.3.0"

        const val desktopSpringSecurity = "org.springframework.security:spring-security-crypto:5.7.3"
        const val desktopBouncyCastle = "org.bouncycastle:bcpkix-jdk15on:1.70"
        const val desktopCommonsLogging = "commons-logging:commons-logging:1.2"
    }
}