package com.intive.picover.auth.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.StorageReference
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.model.AuthEvent
import com.intive.picover.common.converters.BitmapConverter
import com.intive.picover.profile.model.Profile
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepository @Inject constructor(
	storageReference: StorageReference,
	private val firebaseAuth: FirebaseAuth,
	private val bitmapConverter: BitmapConverter,
) {

	private val userAvatarReference = storageReference.child("user/${requireUser().uid}")

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

	suspend fun userProfile(): Profile {
		val photoUrl = userAvatarReference.downloadUrl.await()
		return requireUser().let {
			Profile(
				photo = photoUrl,
				name = it.displayName!!,
				email = it.email!!,
			)
		}
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

	suspend fun updateUserAvatar(uri: Uri): Profile {
		userAvatarReference
			.putBytes(bitmapConverter.convertBitmapToBytes(uri).toByteArray())
			.await()
		return userProfile()
	}

	suspend fun updateUserName(userName: String): Profile {
		requireUser().updateProfile(
			userProfileChangeRequest {
				displayName = userName
			},
		).await()
		return userProfile()
	}

	private fun requireUser() =
		firebaseAuth.currentUser!!
}
