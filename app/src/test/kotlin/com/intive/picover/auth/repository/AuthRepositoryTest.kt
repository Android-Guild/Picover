package com.intive.picover.auth.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.model.AuthEvent
import com.intive.picover.common.converters.BitmapConverter
import com.intive.picover.profile.model.Profile
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class AuthRepositoryTest : ShouldSpec(
	{
		isolationMode = IsolationMode.InstancePerTest

		val userPhoto = mockk<Uri>()
		val userName = "Jack Smith"
		val userEmail = "test@gmail.com"
		val userUid = "test1234567890"
		val profile = Profile(userPhoto, userName, userEmail)
		val byteArrayOutputStream = mockk<ByteArrayOutputStream>(relaxed = true)
		val firebaseAuth: FirebaseAuth = mockk(relaxUnitFun = true) {
			every { currentUser } returns mockk {
				every { photoUrl } returns userPhoto
				every { displayName } returns userName
				every { email } returns userEmail
				every { uid } returns userUid
			}
		}
		val bitmapConverter = mockk<BitmapConverter> {
			every { convertBitmapToBytes(userPhoto) } returns byteArrayOutputStream
		}
		val referenceToUserAvatar = mockk<StorageReference> {
			every { putBytes(byteArrayOutputStream.toByteArray()) } returns mockk {
				every { isComplete } returns true
				every { exception } returns null
				every { isCanceled } returns false
				every { result } returns mockk()
			}
		}
		val storageReference: StorageReference = mockk(relaxed = true) {
			every { child("user/$userUid") } returns referenceToUserAvatar
		}
		val tested = AuthRepository(storageReference, firebaseAuth, bitmapConverter)

		beforeSpec {
			mockkStatic("kotlinx.coroutines.tasks.TasksKt")
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
			every { firebaseAuth.currentUser!!.delete() } returns mockk {
				coEvery { await() } returns mockk()
			}

			val result = tested.deleteAccount()

			verify { firebaseAuth.currentUser!!.delete() }
			result shouldBe AccountDeletionResult.Success
		}

		should("return ReAuthenticationNeeded WHEN deleteAccount called AND delete failed with FirebaseAuthRecentLoginRequiredException") {
			every { firebaseAuth.currentUser!!.delete() } returns mockk {
				coEvery { await() } throws mockk<FirebaseAuthRecentLoginRequiredException>()
			}

			val result = tested.deleteAccount()

			result shouldBe AccountDeletionResult.ReAuthenticationNeeded
		}

		should("call StorageReference.putBytes WHEN updateUserAvatar called") {
			every { referenceToUserAvatar.downloadUrl } returns mockk {
				coEvery { await() } returns userPhoto
			}

			tested.updateUserAvatar(userPhoto)

			verify { referenceToUserAvatar.putBytes(byteArrayOutputStream.toByteArray()) }
		}

		should("return Profile WHEN updateUserAvatar called") {
			every { referenceToUserAvatar.downloadUrl } returns mockk {
				coEvery { await() } returns userPhoto
			}

			tested.updateUserAvatar(userPhoto) shouldBe profile
		}

		should("return Profile WHEN userProfile called") {
			every { referenceToUserAvatar.downloadUrl } returns mockk {
				coEvery { await() } returns userPhoto
			}

			tested.userProfile() shouldBe profile
		}
	},
)
