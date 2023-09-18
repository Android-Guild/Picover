import org.gradle.accessors.dm.LibrariesForLibs

plugins {
	`java-library`
}

val ktlint by configurations.creating

val ktlintCheck by tasks.creating(JavaExec::class) {
	description = "Check Kotlin code style."
	classpath = ktlint
	mainClass.set("com.pinterest.ktlint.Main")
	args = listOf(
		"**/src/**/*.kt",
		"**/*.kts",
		"!**/build/**",
	)
}

// https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

dependencies {
	ktlint(libs.ktlint.cli)
	ktlint(project(":ktlint-ruleset"))
}
