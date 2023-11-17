@file:Suppress("ktlint:standard:filename")

package com.intive.picover.shared

import kotlin.test.DefaultAsserter.assertTrue
import org.junit.jupiter.api.Test

class AndroidGreetingTest {

	@Test
	fun testExample() {
		assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
	}
}
