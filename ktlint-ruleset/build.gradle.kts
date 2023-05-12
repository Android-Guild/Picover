plugins {
	kotlin("jvm")
}

dependencies {
	val version = "0.49.0"
	implementation("com.pinterest.ktlint:ktlint-core:$version")
	implementation("com.pinterest.ktlint:ktlint-cli-ruleset-core:$version")
}
