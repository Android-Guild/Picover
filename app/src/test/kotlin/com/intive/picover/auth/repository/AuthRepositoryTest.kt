package com.intive.picover.auth.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.model.AuthEvent
import com.intive.picover.profile.model.Profile
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class AuthRepositoryTest : ShouldSpec(
	{
		val firebaseAuth: FirebaseAuth = mockk(relaxUnitFun = true)
		val tested = AuthRepository(firebaseAuth)
		val userPhoto = mockk<Uri>()
		val userName = "Jack Smith"
		val userEmail = "test@gmail.com"

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

		should("return Profile data WHEN userProfile called") {
			val profile = Profile(userPhoto, userName, userEmail)
			every { firebaseAuth.currentUser!! } returns mockk {
				every { photoUrl } returns userPhoto
				every { displayName } returns userName
				every { email } returns userEmail
			}

			tested.userProfile() shouldBe profile
		}
	},
)
