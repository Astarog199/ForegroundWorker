plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.exampleworker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.exampleworker"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    java only
    implementation("androidx.work:work-runtime:2.9.0")
//    Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:2.9.0")
//    optional - RxJava2 support
    implementation("androidx.work:work-rxjava2:2.9.0")
//    optional - GCMNetworkManager support
    implementation("androidx.work:work-gcm:2.9.0")
//    optional - Test helpers
    androidTestImplementation("androidx.work:work-testing:2.9.0")
//    optional - Multiprocess support
    implementation("androidx.work:work-multiprocess:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
}