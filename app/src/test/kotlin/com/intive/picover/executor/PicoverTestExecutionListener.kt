package com.intive.picover.executor

import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestPlan

internal class PicoverTestExecutionListener : TestExecutionListener {

	override fun testPlanExecutionStarted(testPlan: TestPlan?) {
		Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
			throw throwable
		}
	}
}
