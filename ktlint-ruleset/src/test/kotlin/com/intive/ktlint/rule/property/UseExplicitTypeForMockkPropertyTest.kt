package com.intive.ktlint.rule.property

import com.intive.ktlint.property.UseExplicitTypeForMockkProperty
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import io.kotest.core.spec.style.ShouldSpec

class UseExplicitTypeForMockkPropertyTest : ShouldSpec(
	{
		val assertRule = assertThatRule(::UseExplicitTypeForMockkProperty)

		should("report violation for mockk declaration without explicit type") {
			val code = """
				val user1 = mockk<User>()
				val user2: User = mockk()
			""".trimIndent()

			assertRule(code)
				.hasLintViolationWithoutAutoCorrect(1, 1, "Specify type explicitly for mockk property")
		}
	},
)
