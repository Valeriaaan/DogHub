plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pethub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pethub"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.itextpdf:itext7-core:7.1.15")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    //gif
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.27")

    // dependency for circular ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.facebook.shimmer:shimmer:0.1.0@aar")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
}