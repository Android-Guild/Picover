package com.intive.picover.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.intive.picover.auth.model.AuthEvent
import io.kotest.core.spec.style.ShouldSpec
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

		beforeSpec {
			mockkStatic("kotlinx.coroutines.tasks.TasksKt")
		}

		afterSpec {
			unmockkAll()
		}

		listOf(
			null to AuthEvent.NotLogged,
			mockk<FirebaseUser>() to AuthEvent.Logged,
		).forEach { (user, authEvent) ->
			should("return $authEvent WHEN observeEvents called AND AuthStateListener returned $user user") {
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

		should("call delete on currentUser from FirebaseAuth WHEN deleteAccount called AND currentUser is present") {
			every { firebaseAuth.currentUser!!.delete() } returns mockk {
				coEvery { await() } returns mockk()
			}

			tested.deleteAccount()

			verify { firebaseAuth.currentUser!!.delete() }
		}
	},
)
