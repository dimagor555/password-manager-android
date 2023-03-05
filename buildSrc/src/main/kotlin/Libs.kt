object Libs {
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

    const val napier = "io.github.aakira:napier:2.6.1"

    const val apacheCodec = "commons-codec:commons-codec:1.15"

    const val settingsNoArg = "com.russhwolf:multiplatform-settings-no-arg:1.0.0"

    const val kase64 = "de.peilicke.sascha:kase64:1.0.6"

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
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
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

    object Ktor {
        private const val version = "2.2.2"
        const val clientCore = "io.ktor:ktor-client-core:$version"
        const val clientCio = "io.ktor:ktor-client-cio:$version"
        const val clientAndroid = "io.ktor:ktor-client-android:$version"
        const val clientLogging = "io.ktor:ktor-client-logging:$version"
        const val clientSerialization = "io.ktor:ktor-client-serialization:$version"
        const val clientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"

        const val serverCore = "io.ktor:ktor-server-core:$version"
        const val serverCio = "io.ktor:ktor-server-cio:$version"
        const val serverNetty = "io.ktor:ktor-server-netty:$version"
        const val serverHostCommon = "io.ktor:ktor-server-host-common:$version"
        const val serverCallLogging = "io.ktor:ktor-server-call-logging:$version"
        const val serverContentNegotiation = "io.ktor:ktor-server-content-negotiation:$version"

        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }

    object Slf4j {
        private const val version = "2.0.6"
        const val slf4jApi= "org.slf4j:slf4j-api:2.0.6"
        const val slf4jSimple = "org.slf4j:slf4j-simple:2.0.6"
    }
}