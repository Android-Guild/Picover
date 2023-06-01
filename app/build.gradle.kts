plugins {
	id("com.android.application")
	kotlin("android")
	kotlin("kapt")
	id("com.google.dagger.hilt.android")
	id("com.google.gms.google-services")
	id("com.google.android.gms.oss-licenses-plugin")
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
	implementation(platform("androidx.compose:compose-bom:2023.05.01"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.compose.material:material-icons-extended")
	implementation("androidx.compose.material3:material3-window-size-class")
	implementation("androidx.core:core-ktx:1.10.1")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
	implementation("androidx.activity:activity-compose:1.7.2")
	implementation("androidx.navigation:navigation-compose:2.5.3")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.gms:play-services-oss-licenses:17.0.1")
	implementation("com.google.gms:google-services:4.3.15")
	implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
	implementation("com.google.firebase:firebase-analytics-ktx")
	implementation("com.google.firebase:firebase-storage-ktx")
	implementation("com.google.dagger:hilt-android:2.46.1")
	kapt("com.google.dagger:hilt-android-compiler:2.46.1")
	implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
	implementation("com.github.skydoves:landscapist-coil:2.2.0")
	implementation("com.google.android.material:material:1.9.0")
	implementation("com.google.firebase:firebase-auth-ktx")
	implementation("com.firebaseui:firebase-ui-auth:8.0.2")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
	testImplementation(platform("io.kotest:kotest-bom:5.6.2"))
	testImplementation("io.kotest:kotest-runner-junit5")
	testImplementation("io.mockk:mockk:1.13.5")
}

