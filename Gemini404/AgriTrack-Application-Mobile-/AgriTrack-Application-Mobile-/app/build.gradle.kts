plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.agritrack"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.agritrack"
        minSdk = 26
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
        // ✅ reCAPTCHA FIX : UNE SEULE fois
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // ✅ reCAPTCHA DESUGARING : UNE SEULE version 2.1.3
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.google.android.material:material:1.11.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room Database
    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // MapLibre
    implementation("org.maplibre.gl:android-sdk:9.6.0")

    // GPS
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // CameraX
    val camerax_version = "1.2.3"
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-view:1.2.3")

    // ML Kit
    implementation("com.google.mlkit:barcode-scanning:17.0.2")

    // Material3 + Core
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // PDF iText
    implementation("com.itextpdf:itextpdf:5.5.13.3")
    implementation("com.itextpdf:kernel:7.2.5")
    implementation("com.itextpdf:layout:7.2.5")

    // Retrofit Exchange API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ✅ reCAPTCHA Enterprise OFFICIEL
//    implementation("com.google.android.recaptcha:recaptcha:18.8.0")
    implementation("com.google.android.gms:play-services-safetynet:18.1.0")



        // ❌ SUPPRIME TOUTES libs Gemini

        // ✅ HTTP + JSON (100% compatible Java/Android)
        implementation ("com.squareup.okhttp3:okhttp:4.12.0")
        implementation ("com.google.code.gson:gson:2.10.1")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

        // ... tes autres dépendances


}
