

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serializaion)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.videoapp"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.videoapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug{
            buildConfigField("String","BASE_URL","\"https://api.coverr.co/videos/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","BASE_URL","\"https://api.coverr.co/videos/\"")
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
        }
    }

}

dependencies {
    //ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    //koin
    implementation(libs.koin.androidx.compose)
    //navigation
    implementation(libs.androidx.navigation.compose)
    //exo player
    implementation(libs.androidx.media3.exoplayer) // [Required] androidx.media3 ExoPlayer dependency
    implementation(libs.androidx.media3.session) // [Required] MediaSession Extension dependency
    implementation(libs.androidx.media3.ui) // [Required] Base Player UI
    //swipe refresh layout
    implementation(libs.accompanist.swiperefresh)
    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit.junit)
    ksp(libs.androidx.room.compiler)

    implementation(libs.gson)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.exoplayer.hls)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Local unit tests
    testImplementation(libs.androidx.core)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.mockk)
    debugImplementation(libs.ui.test.manifest)


    // Instrumentation tests
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test.v151)
    androidTestImplementation( libs.androidx.core.testing.v210)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.core.ktx)
    androidTestImplementation( libs.mockwebserver)
    androidTestImplementation( libs.mockk.android)
    androidTestImplementation(libs.androidx.runner)
}