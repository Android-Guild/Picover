package com.intive.picover.images.viewmodel

import android.net.Uri
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.images.repository.ImagesRepository
import com.intive.picover.images.viewmodel.ImagesStorageState.Error
import com.intive.picover.images.viewmodel.ImagesStorageState.Loading
import com.intive.picover.images.viewmodel.ImagesStorageState.Success
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk

internal class ImagesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())
		isolationMode = IsolationMode.InstancePerTest

		val repo: ImagesRepository = mockk()
		val tested by lazy { ImagesViewModel(repo) }

		should("set Loading state WHEN initialized") {
			coEvery { repo.fetchImages() } just Awaits

			tested.uiState.value shouldBe Loading
		}

		should("set Error state WHEN error is thrown") {
			val throwable = Throwable()
			coEvery { repo.fetchImages() } throws throwable

			tested.uiState.value shouldBe Error(throwable)
		}

		should("set Success state with uris WHEN uris are returned") {
			val uris: List<Uri> = listOf(mockk())
			coEvery { repo.fetchImages() } returns uris

			tested.uiState.value shouldBe Success(uris)
		}
	},
)
