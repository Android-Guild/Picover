package com.intive.picover.images.viewmodel

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.mockkAnswer
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.images.repository.ImagesRepository
import com.intive.picover.photos.usecase.ScheduleUploadPhotoUseCase
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk

internal class ImagesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val imagesRepository: ImagesRepository = mockk(relaxed = true)
		val scheduleUploadPhotoUseCase: ScheduleUploadPhotoUseCase = mockk(relaxed = true)
		val snackbarHostState: SnackbarHostState = mockk(relaxed = true)

		should("set state WHEN fetchImages called") {
			val uris: List<Uri> = listOf(mockk())
			listOf(
				Loading to mockkAnswer<List<Uri>> { just(Awaits) },
				Error to mockkAnswer { throws(Throwable()) },
				Loaded(uris) to mockkAnswer { returns(uris) },
			).forAll { (state, answers) ->
				coEvery { imagesRepository.fetchImages() }.answers()

				val tested = ImagesViewModel(imagesRepository, mockk(), mockk())

				tested.state.value shouldBe state
			}
		}

		should("show snack for finished schedule upload use case") {
			listOf(
				mockkAnswer<Unit> { throws(Throwable()) },
				mockkAnswer { just(Runs) },
			).forAll { answer ->
				val photoUri: Uri = mockk()
				every { scheduleUploadPhotoUseCase(photoUri) }.answer()
				val tested = ImagesViewModel(imagesRepository, scheduleUploadPhotoUseCase, snackbarHostState)

				tested.scheduleUploadPhoto(photoUri)

				coVerify { snackbarHostState.showSnackbar(message = any()) }
			}
		}
	},
)
