buildscript {
	dependencies {
		classpath("com.google.android.gms:oss-licenses-plugin:0.10.6")
	}
}

plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.android.library) apply false
	alias(libs.plugins.kotlin.android) apply false
	alias(libs.plugins.kotlin.ksp) apply false
	alias(libs.plugins.kotlin.multiplatform) apply false
	alias(libs.plugins.google.services) apply false
	alias(libs.plugins.hilt.android) apply false
	alias(libs.plugins.firebase.appdistribution) apply false
	alias(libs.plugins.firebase.crashlytics) apply false
	alias(libs.plugins.kotest) apply false
	`picover-ktlint` apply true
}

subprojects {
	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
