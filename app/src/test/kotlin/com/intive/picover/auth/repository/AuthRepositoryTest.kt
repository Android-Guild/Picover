package com.intive.picover.auth.repository

import android.net.Uri
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.StorageReference
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.model.AuthEvent
import com.intive.picover.profile.model.Profile
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class AuthRepositoryTest : ShouldSpec(
	{
		isolationMode = IsolationMode.InstancePerTest

		val userPhoto: Uri = mockk()
		val userName = "Jack Smith"
		val userEmail = "test@gmail.com"
		val userUid = "test1234567890"
		val profile = Profile(userPhoto, userName, userEmail)
		val firebaseAuth: FirebaseAuth = mockk(relaxUnitFun = true) {
			every { currentUser } returns mockk {
				every { photoUrl } returns userPhoto
				every { displayName } returns userName
				every { email } returns userEmail
				every { uid } returns userUid
			}
		}
		val referenceToUserAvatar: StorageReference = mockk {
			every { putFile(userPhoto) } returns mockk {
				every { isComplete } returns true
				every { exception } returns null
				every { isCanceled } returns false
				every { result } returns mockk()
			}
		}
		val storageReference: StorageReference = mockk(relaxed = true) {
			every { child("user/$userUid") } returns referenceToUserAvatar
		}
		val tested = AuthRepository(storageReference, firebaseAuth)

		beforeSpec {
			mockkStatic("kotlinx.coroutines.tasks.TasksKt")
			mockkStatic(TextUtils::isEmpty)
		}

		afterSpec {
			unmockkAll()
		}

		should("return authEvent depending on user returned from AuthStateListener WHEN observeEvents called") {
			listOf(
				null to AuthEvent.NotLogged,
				mockk<FirebaseUser>() to AuthEvent.Logged,
			).forAll { (user, authEvent) ->
				val listenerSlot = slot<FirebaseAuth.AuthStateListener>()
				every { firebaseAuth.addAuthStateListener(capture(listenerSlot)) } answers {
					listenerSlot.captured.onAuthStateChanged(
						mockk {
							every { currentUser } returns user
						},
					)
				}

				tested.observeEvents().first() shouldBe authEvent
			}
		}

		should("call logout on FirebaseAuth WHEN logout called") {
			tested.logout()

			verify { firebaseAuth.signOut() }
		}

		should("call delete on currentUser from FirebaseAuth AND return Success WHEN deleteAccount called AND delete succeeded") {
			coEvery { firebaseAuth.currentUser!!.delete().await() } returns mockk()

			val result = tested.deleteAccount()

			verify { firebaseAuth.currentUser!!.delete() }
			result shouldBe AccountDeletionResult.Success
		}

		should("return ReAuthenticationNeeded WHEN deleteAccount called AND delete failed with FirebaseAuthRecentLoginRequiredException") {
			coEvery { firebaseAuth.currentUser!!.delete().await() } throws mockk<FirebaseAuthRecentLoginRequiredException>()

			val result = tested.deleteAccount()

			result shouldBe AccountDeletionResult.ReAuthenticationNeeded
		}

		should("call StorageReference.putFile WHEN updateUserAvatar called") {
			coEvery { referenceToUserAvatar.downloadUrl.await() } returns userPhoto

			tested.updateUserAvatar(userPhoto)

			verify { referenceToUserAvatar.putFile(userPhoto) }
		}

		should("return Profile WHEN updateUserAvatar called") {
			coEvery { referenceToUserAvatar.downloadUrl.await() } returns userPhoto

			val result = tested.updateUserAvatar(userPhoto)

			result shouldBeSuccess profile
		}

		should("return Profile WHEN userProfile called") {
			coEvery { referenceToUserAvatar.downloadUrl.await() } returns userPhoto

			val result = tested.userProfile()

			result shouldBeSuccess profile
		}

		should("set display name WHEN updateUserName called") {
			val slot = slot<UserProfileChangeRequest>()
			every { firebaseAuth.currentUser!! } returns mockk {
				every { displayName } returns "Marian Kowalski"
				every { email } returns userEmail
				every { uid } returns userUid
				coEvery { updateProfile(capture(slot)).await() } returns mockk()
			}
			every { TextUtils.isEmpty(any()) } returns true
			coEvery { referenceToUserAvatar.downloadUrl.await() } returns userPhoto

			val result = tested.updateUserName("Marian Kowalski")

			result shouldBeSuccess Profile(userPhoto, "Marian Kowalski", userEmail)
			slot.captured.displayName!! shouldBeEqual "Marian Kowalski"
		}

		should("not call storageReference WHEN class is created") {
			verify { storageReference wasNot Called }
		}
	},
)
