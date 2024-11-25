import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}



android {
    namespace = "com.app.weather.core"
    compileSdk = 34

    val keystoreProperties = Properties()
    val keystoreFile = rootProject.file("key.properties")
    if (keystoreFile.exists()) {
        keystoreProperties.load(keystoreFile.inputStream())
    }
    val apiKey = keystoreProperties.getProperty("API_KEY", "")
    signingConfigs {
        create("production-release") {
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
            storeFile = file(keystoreProperties.getProperty("KEYSTORE_PATH"))
            storePassword = keystoreProperties.getProperty("KEYSTORE_PASSWORD")
        }
        create("staging-release") {
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
            storeFile = file(keystoreProperties.getProperty("KEYSTORE_PATH"))
            storePassword = keystoreProperties.getProperty("KEYSTORE_PASSWORD")
        }
    }
    defaultConfig {
//        applicationId = "com.app.weather"
        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("production-release")
        }
    }
    val flavorDimension = "default"


    flavorDimensions(flavorDimension)
    productFlavors {
        //TODO change endpoint
        create("staging") {
            dimension = flavorDimension
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://api.openweathermap.org\""
            )
            buildConfigField("boolean", "enableNetworkLogging", "true")
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
        }

        //TODO change endpoint
        create("production") {
            dimension = flavorDimension
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://api.openweathermap.org\""
            )
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("boolean", "enableNetworkLogging", "false")
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
//    packaging {
////        resources {
////            excludes += "/META-INF/{AL2.0,LGPL2.1}"
////        }
//    }
}

dependencies {

    implementation(libs.coil.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.googleGson)
    implementation(libs.hiltNavigationCompose)
    implementation(libs.play.services.location)
    implementation(project(":data"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.bundles.retrofit)
    implementation(libs.chucker.library)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.kotlin.codegen)
    //implementation(libs.mo)
    // implementation(libs.chucker.library.noop)
    implementation(libs.okhttp3.logging.interceptor)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation(libs.accompanist.systemuicontroller)
    // Unit testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Retrofit mock server
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

}