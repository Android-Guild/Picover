package com.intive.picover.auth.intent.builder

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.intive.picover.auth.intent.SignInIntent
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor

class SignInIntentBuilderTest : ShouldSpec(
	{
		beforeEach {
			mockkConstructor(EmailBuilder::class)
		}

		should("return intent wrapped by SignInIntent WHEN build called") {
			val providers: AuthUI.IdpConfig = mockk()
			val signInIntent: Intent = mockk()
			val authUi: AuthUI = mockk {
				every { createSignInIntentBuilder() } returns mockk {
					every { setTheme(any()) } returns this
					every { setAvailableProviders(listOf(providers)) } returns this
					every { build() } returns signInIntent
				}
			}
			every { anyConstructed<EmailBuilder>().build() } returns providers

			val result = SignInIntentBuilder.build(authUi)

			result shouldBe SignInIntent(signInIntent)
		}
	},
)
