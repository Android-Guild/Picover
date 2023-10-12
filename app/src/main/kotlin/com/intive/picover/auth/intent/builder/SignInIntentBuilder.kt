package com.intive.picover.auth.intent.builder

import com.firebase.ui.auth.AuthUI
import com.intive.picover.R
import com.intive.picover.auth.intent.SignInIntent

object SignInIntentBuilder {

	fun build(authUi: AuthUI) =
		SignInIntent(
			authUi.createSignInIntentBuilder()
				.setTheme(R.style.Theme_Picover)
				.setAvailableProviders(Configuration.providers)
				.build(),
		)

	private object Configuration {
		val providers = listOf(
			AuthUI.IdpConfig.EmailBuilder().build(),
		)
	}
}
