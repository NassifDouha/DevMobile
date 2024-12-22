plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.test1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.test1"
        minSdk = 29
        targetSdk = 35
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout) // ConstraintLayout
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation(libs.cronet.embedded) // Glide pour les images
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")


    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")


}
