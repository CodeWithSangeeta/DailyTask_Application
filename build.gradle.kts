plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

   //Apply the ksp plugin using the alias
   alias(libs.plugins.ksp)

    //hilt plugins
    alias(libs.plugins.hilt)
}



android {
    namespace = "com.practice.daily_task"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.practice.daily_task"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //compose runtime dependency (live data)
    implementation("androidx.compose.runtime:runtime-livedata:1.9.0")

//    //Room database dependency
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //dependenciey for navigation
    val nav_version = "2.9.3"
    implementation("androidx.navigation:navigation-compose:$nav_version")


    //hilt dependency
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    //Splash screen dependency
    implementation("androidx.core:core-splashscreen:1.0.1")

    //hilt navigation dependency
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //coroutine dependency
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")



}

