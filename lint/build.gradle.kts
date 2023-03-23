plugins {
	kotlin("jvm")
}

dependencies {
	val lintVersion = "30.4.2"
	compileOnly("com.android.tools.lint:lint-api:$lintVersion")
	compileOnly("com.android.tools.lint:lint-checks:$lintVersion")
	testImplementation(platform("io.kotest:kotest-bom:5.5.5"))
	testImplementation("com.android.tools.lint:lint:$lintVersion")
	testImplementation("com.android.tools.lint:lint-tests:$lintVersion")
	testImplementation("io.kotest:kotest-runner-junit5")
}
