@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
	id(libs.plugins.android.application.get().pluginId)
	kotlin("android")
	kotlin("kapt")
	id(libs.plugins.hilt.android.get().pluginId)
	id(libs.plugins.google.services.get().pluginId)
	id(libs.plugins.oss.licenses.get().pluginId)
}

apply {
	from("../scripts/ktlint.gradle.kts")
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
		kotlinCompilerExtensionVersion = "1.4.7"
	}
	packagingOptions {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}

kapt {
	correctErrorTypes = true
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
	kapt(libs.dagger.hilt.compiler)
	implementation(libs.dagger.hilt.navigation)
	implementation(libs.coil)
	implementation(libs.material)
	implementation(libs.firebase.auth)
	implementation(libs.firebaseui.auth)
	debugImplementation(libs.compose.ui.tooling)
	testImplementation(libs.test.kotest.runner)
	testImplementation(libs.test.mockk)
}

