package com.intive.picover.images.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class ImagesRepositoryTest : ShouldSpec(
	{

		val testDispatcher = StandardTestDispatcher()
		val storageReference: StorageReference = mockk(relaxed = true)
		val firebaseStorage: FirebaseStorage = mockk {
			every { reference.child("image") } returns storageReference
		}
		val storageItem: StorageReference = mockk()
		val tested = ImagesRepository(firebaseStorage, testDispatcher)

		beforeSpec {
			mockkStatic("kotlinx.coroutines.tasks.TasksKt")
		}

		afterSpec {
			unmockkAll()
		}

		should("downloaded URIs WHEN storage reference and uri is fetched successfully") {
			runTest(testDispatcher) {
				val listResult: ListResult = mockk {
					every { items } returns listOf(storageItem)
				}
				val uriResult: Uri = mockk()
				every { storageReference.listAll() } returns mockk {
					coEvery { await() } returns listResult
				}
				every { storageItem.downloadUrl } returns mockk {
					coEvery { await() } returns uriResult
				}

				tested.fetchImages() shouldBe listOf(uriResult)
			}
		}

		should("throw exception WHEN fetching storage reference fails") {
			runTest(testDispatcher) {
				every { storageReference.listAll() } returns mockk {
					coEvery { await() } throws Exception()
				}

				shouldThrowExactly<Exception> {
					tested.fetchImages()
				}
			}
		}

		should("throw exception WHEN fetching the uri fails") {
			runTest(testDispatcher) {
				val listResult: ListResult = mockk {
					every { items } returns listOf(storageItem)
				}
				every { storageReference.listAll() } returns mockk {
					coEvery { await() } returns listResult
				}
				every { storageItem.downloadUrl } returns mockk {
					coEvery { await() } throws Exception()
				}

				shouldThrowExactly<Exception> {
					tested.fetchImages()
				}
			}
		}
	},
)
