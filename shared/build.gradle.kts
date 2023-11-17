plugins {
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.android.library)
}

kotlin {
	androidTarget {
		jvmToolchain(17)
	}

	sourceSets {
		commonMain.dependencies {
			implementation(project.dependencies.platform(libs.firebase.bom))
			implementation(libs.firebase.firestore)
		}
		commonTest.dependencies {
			implementation(libs.test.kotlin)
			implementation(libs.test.kotest.runner)
			implementation(libs.test.mockk)
		}
	}
}

android {
	namespace = "com.intive.picover.shared"
	compileSdk = 34
	defaultConfig {
		minSdk = 24
	}
}
