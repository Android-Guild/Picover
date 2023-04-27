plugins {
	kotlin("jvm")
}

dependencies {
	val lintVersion = "31.0.1"
	compileOnly("com.android.tools.lint:lint-api:$lintVersion")
	testImplementation("io.kotest:kotest-runner-junit5:5.6.1")
	testImplementation("com.android.tools.lint:lint:$lintVersion")
	testImplementation("com.android.tools.lint:lint-tests:$lintVersion")
}
