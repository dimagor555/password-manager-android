object Libs {
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

    const val argon2kt = "com.lambdapioneer.argon2kt:argon2kt:1.3.0"

    const val localizedString = "com.github.aartikov:sesame-localized-string:1.2.0-beta1"

    object Compose {
        const val activity = "androidx.activity:activity-compose:1.3.1"

        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"

        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val iconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    }

    object Navigation {
        const val compose = "androidx.navigation:navigation-compose:2.4.0-alpha10"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        const val appCompat = "androidx.appcompat:appcompat:1.3.1"

        const val splashScreen = "androidx.core:core-splashscreen:1.0.0-alpha01"

        const val securityCrypto = "androidx.security:security-crypto:1.1.0-alpha03"
        const val biometric = "androidx.biometric:biometric:1.2.0-alpha04"
    }

    object KotlinX {
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.0"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"
    }

    object MviCompose {
        private const val version = "1.0.0"
        const val core = "io.github.dimagor555.mvicompose:core:$version"
        const val android = "io.github.dimagor555.mvicompose:android:$version"
    }

    object Room {
        private const val roomVersion = "2.3.0"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
    }

    object Koin {
        private const val version = "3.2.0"
        const val core = "io.insert-koin:koin-core:$version"
        const val android = "io.insert-koin:koin-android:$version"
        const val compose = "io.insert-koin:koin-androidx-compose:$version"
    }
}