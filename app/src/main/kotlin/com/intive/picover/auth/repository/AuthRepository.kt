package com.intive.picover.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.model.AuthEvent
import com.intive.picover.profile.model.Profile
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepository @Inject constructor(
	private val firebaseAuth: FirebaseAuth,
) {

	fun observeEvents() =
		callbackFlow {
			val authStateListener = FirebaseAuth.AuthStateListener {
				if (it.currentUser == null) {
					AuthEvent.NotLogged
				} else {
					AuthEvent.Logged
				}.let { authEvent ->
					trySend(authEvent)
				}
			}
			firebaseAuth.addAuthStateListener(authStateListener)
			awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
		}

	fun logout() {
		firebaseAuth.signOut()
	}

	fun userProfile() =
		requireUser().let {
			Profile(
				photo = it.photoUrl,
				name = it.displayName!!,
				email = it.email!!,
			)
		}

	suspend fun deleteAccount(): AccountDeletionResult {
		return try {
			requireUser()
				.delete()
				.await()
			AccountDeletionResult.Success
		} catch (firebaseAuthRecentLoginRequiredException: FirebaseAuthRecentLoginRequiredException) {
			firebaseAuth.signOut()
			AccountDeletionResult.ReAuthenticationNeeded
		}
	}

	private fun requireUser() =
		firebaseAuth.currentUser!!
}
