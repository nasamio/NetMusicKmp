import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting


        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

//            implementation("androidx.compose.animation:animation:1.8.2")

            // 路由
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
            // Enables FileKit without Compose dependencies
            implementation("io.github.vinceglb:filekit-core:0.8.8")
//            // Enables FileKit with Composable utilities
//            implementation("io.github.vinceglb:filekit-compose:0.8.8")
            implementation(libs.coil.compose.core)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)
            implementation(libs.coil.compose)
            // ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.io)

            // 取色
            implementation(libs.kmpalette.core)

            // 媒体库
//            implementation(libs.compose.media.player)

//            implementation(libs.gadulka)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            // gson
            implementation("com.google.code.gson:gson:2.10.1")
            implementation(libs.ktor.client.desktop)

            implementation("uk.co.caprica:vlcj:4.7.1")

            // 下面强制增加java fx依赖 解决在windows平台gadulka无法正常运行的问题
            val fxSuffix = "win"
            implementation("org.openjfx:javafx-base:19:${fxSuffix}")
            implementation("org.openjfx:javafx-graphics:19:${fxSuffix}")
            implementation("org.openjfx:javafx-controls:19:${fxSuffix}")
            implementation("org.openjfx:javafx-swing:19:${fxSuffix}")
            implementation("org.openjfx:javafx-web:19:${fxSuffix}")
            implementation("org.openjfx:javafx-media:19:${fxSuffix}")

            implementation( "net.java.dev.jna:jna:5.13.0"         )   // JNA核心库
            implementation("net.java.dev.jna:jna-platform:5.13.0")   // 包含WindowUtils等平台相关工具

        }
    }
}

android {
    namespace = "com.mio"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mio"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.material3.android)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.mio.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = "com.mio"
            packageVersion = "1.0.0"
        }
    }
}
