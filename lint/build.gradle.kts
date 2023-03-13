plugins {
    kotlin("jvm")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

dependencies {
    val lintVersion = "30.4.2"
	compileOnly("com.android.tools.lint:lint-api:$lintVersion")
	compileOnly("com.android.tools.lint:lint-checks:$lintVersion")
	testImplementation("com.android.tools.lint:lint:$lintVersion")
	testImplementation("com.android.tools.lint:lint-tests:$lintVersion")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}
