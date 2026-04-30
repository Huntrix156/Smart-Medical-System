plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.smartmedicalsystem"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.smartmedicalsystem"
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
    buildFeatures {
        compose = true
    }
}

//dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.compose.ui)
//    implementation(libs.androidx.compose.ui.graphics)
//    implementation(libs.androidx.compose.ui.tooling.preview)
//    implementation(libs.androidx.compose.material3)
//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.compose.material3.lint)
//    implementation(libs.androidx.espresso.core)
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.material3)
//    implementation(libs.firebase.auth.ktx)
////    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
////    implementation("com.google.firebase:firebase-firestore-ktx")
////    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation(libs.firebase.database.ktx)
//    implementation(libs.firebase.database.ktx)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
//    implementation("androidx.compose.material:material-icons-extended") //This library provides a large collection of ready-made Material Design icons for Compose.//
//    implementation("androidx.navigation:navigation-compose:2.8.4") //This is the navigation system for Jetpack Compose apps.//
//    implementation("io.coil-kt:coil-compose:2.0.0")//for the rememberAsyncImagePainter
//    implementation("androidx.room:room-runtime:2.6.1")
//    implementation("androidx.room:room-ktx:2.6.1")
//    annotationProcessor("androidx.room:room-compiler:2.6.1")
//    implementation("androidx.navigation:navigation-compose:2.7.7")
//}



dependencies {
    // ── Core ────────────────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ── Compose ─────────────────────────────────────────────────────────────
    implementation(platform(libs.androidx.compose.bom))   // BOM manages all compose versions
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended")

    // ── Navigation ──────────────────────────────────────────────────────────
    implementation("androidx.navigation:navigation-compose:2.8.4")

    // ── Firebase ────────────────────────────────────────────────────────────
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-auth")        // no -ktx needed anymore
    implementation("com.google.firebase:firebase-database")    // no -ktx needed anymore
    implementation("com.google.firebase:firebase-firestore")   // no -ktx needed anymore

    // ── Room ────────────────────────────────────────────────────────────────
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.runtime)
    annotationProcessor("androidx.room:room-compiler:2.6.1")  // use kapt or ksp if using Kotlin

    // ── Image loading ───────────────────────────────────────────────────────
    implementation("io.coil-kt:coil-compose:2.6.0")

    // ── Testing ─────────────────────────────────────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
     //-------------roles selection-----------
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.4")
}