package com.intive.picover.auth.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.model.AuthEvent
import com.intive.picover.profile.model.Profile
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepository @Inject constructor(
	storageReference: StorageReference,
	private val firebaseAuth: FirebaseAuth,
) {

	private val userAvatarReference by lazy { storageReference.child("user/${requireUser().uid}") }

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

	suspend fun userProfile() =
		try {
			val photoUrl = userAvatarReference.downloadUrl.await()
			Result.success(createProfile(photoUrl))
		} catch (storageException: StorageException) {
			Result.success(createProfile(null))
		} catch (exception: Exception) {
			Result.failure(exception)
		}

	suspend fun deleteAccount() =
		try {
			requireUser()
				.delete()
				.await()
			AccountDeletionResult.Success
		} catch (firebaseAuthRecentLoginRequiredException: FirebaseAuthRecentLoginRequiredException) {
			firebaseAuth.signOut()
			AccountDeletionResult.ReAuthenticationNeeded
		}

	suspend fun updateUserAvatar(uri: Uri) =
		try {
			userAvatarReference
				.putFile(uri)
				.await()
			userProfile()
		} catch (exception: Exception) {
			Result.failure(exception)
		}

	suspend fun updateUserName(userName: String) =
		try {
			requireUser()
				.updateProfile(
					userProfileChangeRequest {
						displayName = userName
					},
				).await()
			userProfile()
		} catch (exception: Exception) {
			Result.failure(exception)
		}

	private fun requireUser() =
		firebaseAuth.currentUser!!

	private fun createProfile(photoUri: Uri?) =
		requireUser().let {
			Profile(
				photo = photoUri,
				name = it.displayName!!,
				email = it.email!!,
			)
		}
}
