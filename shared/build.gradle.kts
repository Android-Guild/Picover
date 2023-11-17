plugins {
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.android.library)
}

kotlin {
	androidTarget {
		compilations.all {
			kotlinOptions {
				jvmTarget = "1.8"
			}
		}
	}

	sourceSets {
		commonMain.dependencies {
			// put your multiplatform dependencies here
		}
		commonTest.dependencies {
			implementation(libs.test.kotlin)
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
