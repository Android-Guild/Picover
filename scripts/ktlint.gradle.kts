val ktlint by configurations.creating

dependencies {
	ktlint("com.pinterest:ktlint:0.49.0")
	ktlint(project(":ktlint-ruleset"))
}

val ktlintCheck by tasks.creating(JavaExec::class) {
	description = "Check Kotlin code style."
	classpath = ktlint
	mainClass.set("com.pinterest.ktlint.Main")
	args = listOf("src/**/*.kt")
}
