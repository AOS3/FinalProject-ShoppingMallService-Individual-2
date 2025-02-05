import com.android.manifmerger.Actions.load
import java.io.FileInputStream
import java.util.Properties

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

configurations.all {
    resolutionStrategy {
        // force와 exclude 구문을 수정
        force("com.google.protobuf:protobuf-javalite:3.21.12")

        // 충돌하는 의존성 제외
        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }
}

android {
// 추가
    val properties =
        Properties().apply {
            load(FileInputStream(rootProject.file("local.properties")))
        }

    buildFeatures {
        buildConfig = true
    }
    namespace = "com.teammeditalk.medicationproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.teammeditalk.medicationproject"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KAKAO_MAP_KEY", properties.getProperty("KAKAO_MAP_KEY"))
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
        buildConfig = true
    }
}

dependencies {
    // implementation("com.google.type:type:0.12.0")
    implementation("com.google.protobuf:protobuf-javalite:3.21.12")

    implementation(libs.firebase.firestore)

    implementation(libs.android)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.fragment.ktx.v183)

    implementation(libs.androidx.datastore)

    implementation(libs.protobuf.kotlin)
    implementation(libs.androidx.datastore.core)

    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.serialization.kotlinx.json)
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
                register("kotlin") { option("lite") }
                register("java") { option("lite") }
            }
        }
    }
}
