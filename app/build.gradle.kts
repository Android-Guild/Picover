@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.noarg.gradle.NoArgExtension

plugins {
	id(libs.plugins.android.application.get().pluginId)
	kotlin("android")
	id(libs.plugins.kotlin.ksp.get().pluginId)
	id(libs.plugins.hilt.android.get().pluginId)
	id(libs.plugins.google.services.get().pluginId)
	id(libs.plugins.oss.licenses.get().pluginId)
	id(libs.plugins.kotlin.noarg.get().pluginId)
}

android {
	namespace = "com.intive.picover"
	compileSdk = 33

	defaultConfig {
		applicationId = "com.intive.picover"
		minSdk = 26
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.3"
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}

configure<NoArgExtension> {
	annotation("com.intive.picover.common.annotation.NoArgConstructor")
}

dependencies {
	lintChecks(project(":lint"))
	implementation(platform(libs.compose.bom))
	implementation(libs.compose.ui.ui)
	implementation(libs.compose.ui.preview)
	implementation(libs.compose.material3)
	implementation(libs.compose.material.icons)
	implementation(libs.compose.material3.window.size)
	implementation(libs.android.core)
	implementation(libs.android.lifecycle)
	implementation(libs.android.compose.activity)
	implementation(libs.android.compose.navigation)
	implementation(libs.android.google.oss.licenses)
	implementation(libs.android.google.services)
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.analytics)
	implementation(libs.firebase.storage)
	implementation(libs.dagger.hilt.android)
	ksp(libs.dagger.hilt.compiler)
	implementation(libs.dagger.hilt.navigation)
	implementation(libs.coil)
	implementation(libs.material)
	implementation(libs.firebase.auth)
	implementation(libs.firebaseui.auth)
	implementation(libs.firebase.database)
	debugImplementation(libs.compose.ui.tooling)
	testImplementation(libs.test.kotest.runner)
	testImplementation(libs.test.mockk)
	testCompileOnly("org.jetbrains.kotlin:kotlin-reflect:1.8.20") {
		because("Needed to locally trigger single kotest test - check new versions of kotlin and kotest plugins to fix this workaround")
	}
}
