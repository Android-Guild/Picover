package com.intive.picover.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.intive.picover.auth.model.AuthEvent
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

	// TODO #100 Handle FirebaseAuthRecentLoginRequiredException
	suspend fun deleteAccount() {
		firebaseAuth.currentUser!!
			.delete()
			.await()
	}
}
