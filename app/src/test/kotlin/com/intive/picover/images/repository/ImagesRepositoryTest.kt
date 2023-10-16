package com.intive.picover.images.repository

import android.net.Uri
import com.google.firebase.storage.StorageReference
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

class ImagesRepositoryTest : ShouldSpec(
	{

		val testDispatcher = StandardTestDispatcher()
		val imageReference: StorageReference = mockk()
		val storageReference: StorageReference = mockk {
			every { child("image") } returns imageReference
		}
		val tested = ImagesRepository(storageReference, testDispatcher)

		beforeSpec {
			mockkStatic("kotlinx.coroutines.tasks.TasksKt")
		}

		afterSpec {
			unmockkAll()
		}

		should("downloaded URIs WHEN storage reference and uri is fetched successfully") {
			runTest(testDispatcher) {
				val uriResult: Uri = mockk()
				coEvery { imageReference.listAll().await().items } returns listOf(
					mockk {
						coEvery { downloadUrl.await() } returns uriResult
					},
				)

				tested.fetchImages() shouldBe listOf(uriResult)
			}
		}

		should("throw exception WHEN fetching storage reference fails") {
			runTest(testDispatcher) {
				coEvery { imageReference.listAll().await() } throws Exception()

				shouldThrowExactly<Exception> {
					tested.fetchImages()
				}
			}
		}

		should("throw exception WHEN fetching the uri fails") {
			runTest(testDispatcher) {
				coEvery { imageReference.listAll().await().items } returns listOf(
					mockk {
						coEvery { downloadUrl.await() } throws Exception()
					},
				)

				shouldThrowExactly<Exception> {
					tested.fetchImages()
				}
			}
		}
	},
)
