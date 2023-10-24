package com.intive.ktlint.rule.property

import com.intive.ktlint.property.UseExplicitTypeForMockkProperty
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll

internal class UseExplicitTypeForMockkPropertyTest : ShouldSpec(
	{
		val assertRule = assertThatRule(::UseExplicitTypeForMockkProperty)

		should("report violation for mockk declaration without explicit type") {
			listOf(
				"val user = mockk<User>()",
				"val user: User = mockk<User>()",
			).forAll { code ->
				assertRule(code).hasLintViolationWithoutAutoCorrect(1, 1, "Specify type explicitly for mockk property")
			}
		}

		should("not report violation for mockk declaration without explicit type") {
			listOf(
				"val user = User(5)",
				"val user = User(mockk())",
				"val user: User = mockk()",
				"val user: User = User(mockk())",
			).forAll { code ->
				assertRule(code).hasNoLintViolations()
			}
		}
	},
)
