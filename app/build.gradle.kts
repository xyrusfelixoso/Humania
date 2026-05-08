plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services) // Mao ni ang plugin sa Step 2 sa picture
}

android {
    namespace = "com.example.humania"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.humania"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Step 2: Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    
    // Step 2: Add Firebase Analytics and other products
    // Note: Wala na'y version diri kay BoM na ang nag-handle
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
