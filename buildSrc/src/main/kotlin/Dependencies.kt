object Dependencies {

    object Plugins {

        private const val GRADLE_VERSION = "7.1.0"
        private const val KOTLIN_VERSION = "1.6.10"

        const val GRADLE = "com.android.tools.build:gradle:$GRADLE_VERSION"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    }

    object Core {

        private const val CORE_VERSION = "1.7.0"
        private const val APPCOMPAT_VERSION = "1.4.1"
        private const val MATERIAL_VERSION = "1.5.0"
        private const val SWIPE_REFRESH_LAYOUT_VERSION = "1.1.0"

        private const val RECYCLER_VIEW_VERSION = "1.2.1"

        const val CORE = "androidx.core:core-ktx:$CORE_VERSION"
        const val APP_COMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"
        const val MATERIAL = "com.google.android.material:material:$MATERIAL_VERSION"
        const val SWIPE_REFRESH_LAYOUT =
            "androidx.swiperefreshlayout:swiperefreshlayout:$SWIPE_REFRESH_LAYOUT_VERSION"
        const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:$RECYCLER_VIEW_VERSION"
    }

    object Elmslie {

        private const val VERSION = "1.3.0"

        const val CORE = "com.github.vivid-money.elmslie:elmslie-core:$VERSION"
        const val ANDROID = "com.github.vivid-money.elmslie:elmslie-android:$VERSION"
        const val RXJAVA = "com.github.vivid-money.elmslie:elmslie-rxjava-2:$VERSION"
    }

    object RX {

        private const val RXJAVA_VERSION = "2.2.21"
        private const val RXANDROID_VERSION = "2.1.1"

        const val RXJAVA = "io.reactivex.rxjava2:rxjava:$RXJAVA_VERSION"
        const val RXANDROID = "io.reactivex.rxjava2:rxandroid:$RXANDROID_VERSION"
    }

    object Dagger {

        private const val DAGGER_VERSION = "2.40.5"
        const val CORE = "com.google.dagger:dagger:$DAGGER_VERSION"
        const val KAPT = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"
    }

    object Network {

        private const val RETROFIT_VERSION = "2.9.0"
        private const val MOSHI_VERSION = "2.4.0"
        private const val OKHTTP_VERSION = "4.9.3"

        const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
        const val ADAPTER = "com.squareup.retrofit2:adapter-rxjava2:$RETROFIT_VERSION"
        const val CONVERTER = "com.squareup.retrofit2:converter-moshi:$MOSHI_VERSION"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
        const val INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"
    }

    object Room {

        private const val VERSION = "2.4.1"

        const val RUNTIME = "androidx.room:room-runtime:$VERSION"
        const val RXJAVA = "androidx.room:room-rxjava2:$VERSION"
        const val KAPT = "androidx.room:room-compiler:$VERSION"
    }

    object AdapterDelegates {

        private const val VERSION = "4.3.0"

        const val CORE = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$VERSION"
        const val LAYOUT =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:$VERSION"
    }

    object ViewBindingPropertyDelegate {

        private const val VERSION = "1.5.3"
        const val NO_REFLECTION =
            "com.github.kirich1409:viewbindingpropertydelegate-noreflection:$VERSION"
    }

    object Other {

        private const val SHIMMER_VERSION = "0.5.0"
        private const val FRESCO_VERSION = "2.6.0"

        const val SHIMMER = "com.facebook.shimmer:shimmer:$SHIMMER_VERSION"
        const val FRESCO = "com.facebook.fresco:fresco:$FRESCO_VERSION"
        const val FRESCO_GIF = "com.facebook.fresco:animated-gif:$FRESCO_VERSION"
    }
}