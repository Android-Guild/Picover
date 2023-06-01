buildscript {
	dependencies {
		classpath("com.google.android.gms:oss-licenses-plugin:0.10.4")
	}
}

plugins {
	id("com.android.application") version "8.0.2" apply false
	id("org.jetbrains.kotlin.android") version "1.8.21" apply false
	id("com.google.gms.google-services") version "4.3.15" apply false
	id("com.google.dagger.hilt.android") version "2.44.2" apply false
}

subprojects {
	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
