plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.kapt)
    id("kotlinx-serialization")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.teammeditalk.medicationproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.teammeditalk.medicationproject"
        minSdk = 30
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
                "proguard-rules.pro",
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

    implementation("androidx.fragment:fragment-ktx:1.8.3")

    implementation("androidx.datastore:datastore:1.1.2")

    implementation(libs.protobuf.kotlin)
    implementation(libs.androidx.datastore.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)

    kapt(libs.processor.v0813)
    implementation(libs.annotation.v0813)
    implementation(libs.core.v0813)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.androidx.connect.client)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)
    implementation(libs.firebase.ui.auth)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}

protobuf {
    val protobufVersion =
        libs.versions.protobuf
            .asProvider()
            .get()

    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                register("java") { option("lite") }
                register("kotlin") { option("lite") }
            }
        }
    }
}
