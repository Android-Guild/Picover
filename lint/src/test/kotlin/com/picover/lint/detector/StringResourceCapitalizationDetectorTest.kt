package com.picover.lint.detector

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.jupiter.api.Test

internal class StringResourceCapitalizationDetectorTest {

	@Test
	fun testDetectingNotCapitalizedBasic() {
		lint().files(
			TestFiles.xml(
				"/res/values/strings.xml",
				"""
					<resources>
						<string name="app_name">Picover</string>
						<string name="Home">Home</string>
						<string name="camera">Camera</string>
					</resources>
                     """,
			).indented(),
		).issues(StringResourceCapitalizationDetector.ISSUE)
			.run()
			.expectErrorCount(2)
			.expectContains("app_name")
			.expectContains("camera")
	}

	@Test
	fun testNotReportForColors() {
		lint().files(
			TestFiles.xml(
				"/res/values/color.xml",
				"""
					<resources>
						<color name="purple_200">#FFBB86FC</color>
						<color name="purple_500">#FF6200EE</color>
					</resources>
                     """,
			).indented(),
		).issues(StringResourceCapitalizationDetector.ISSUE)
			.run()
			.expectClean()
	}
}
