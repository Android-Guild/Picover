package com.intive.picover.images.viewmodel

import android.net.Uri
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.mockkAnswer
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.images.repository.ImagesRepository
import com.intive.picover.photos.usecase.UploadPhotoUseCase
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

internal class ImagesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val imagesRepository: ImagesRepository = mockk(relaxed = true)
		val uploadPhotoUseCase: UploadPhotoUseCase = mockk(relaxed = true)

		should("set state WHEN fetchImages called") {
			val uris: List<Uri> = listOf(mockk())
			listOf(
				Loading to mockkAnswer<List<Uri>> { just(Awaits) },
				Error to mockkAnswer { throws(Throwable()) },
				Loaded(uris) to mockkAnswer { returns(uris) },
			).forAll { (state, answers) ->
				coEvery { imagesRepository.fetchImages() }.answers()

				val tested = ImagesViewModel(imagesRepository, uploadPhotoUseCase)

				tested.state.value shouldBe state
			}
		}

		should("delegate upload photo to use case") {
			val photoUri: Uri = mockk()
			val tested = ImagesViewModel(imagesRepository, uploadPhotoUseCase)

			tested.uploadPhoto(photoUri)

			verify { uploadPhotoUseCase(photoUri) }
		}
	},
)
