plugins {
	kotlin("jvm")
}

dependencies {
	implementation(libs.ktlint.ruleset)
	testImplementation(libs.test.ktlint)
	testImplementation(libs.test.kotest.runner)
	testImplementation(libs.test.slf4j)
}
