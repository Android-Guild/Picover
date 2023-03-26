package com.picover.lint.detector

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import io.kotest.core.spec.style.ShouldSpec

internal class StringResourceCapitalizationDetectorTest : ShouldSpec({

	should("report error for string resources WHEN names are not capitalized") {
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

	should("not report errors for not capitalized color resources") {
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
})
