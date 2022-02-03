plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.Core.CORE)
    implementation(Dependencies.Core.APP_COMPAT)
    implementation(Dependencies.Core.MATERIAL)
    implementation(Dependencies.Core.SWIPE_REFRESH_LAYOUT)

    implementation(Dependencies.Elmslie.CORE)
    implementation(Dependencies.Elmslie.ANDROID)
    implementation(Dependencies.Elmslie.RXJAVA)

    implementation(Dependencies.RX.RXJAVA)
    implementation(Dependencies.RX.RXANDROID)

    implementation(Dependencies.Network.RETROFIT)
    implementation(Dependencies.Network.ADAPTER)
    implementation(Dependencies.Network.CONVERTER)
    implementation(Dependencies.Network.OKHTTP)
    implementation(Dependencies.Network.INTERCEPTOR)

    implementation(Dependencies.Room.RUNTIME)
    implementation(Dependencies.Room.RXJAVA)
    kapt(Dependencies.Room.KAPT)

    implementation(Dependencies.AdapterDelegates.CORE)
    implementation(Dependencies.AdapterDelegates.LAYOUT)

    implementation(Dependencies.Other.SHIMMER)
    implementation(Dependencies.Other.GLIDE)

    implementation(project(mapOf("path" to ":CardStackView")))
}