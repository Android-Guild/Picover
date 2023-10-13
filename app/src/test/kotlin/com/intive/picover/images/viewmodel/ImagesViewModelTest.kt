package com.intive.picover.images.viewmodel

import android.net.Uri
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.testState
import com.intive.picover.images.repository.ImagesRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk

internal class ImagesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		should("set state WHEN fetchImages called") {
			val uris: List<Uri> = listOf(mockk())
			testState<List<Uri>, List<Uri>>(
				loadingAnswer = { just(Awaits) },
				errorAnswer = { throws(Throwable()) },
				loadedAnswer = { returns(uris) },
				loadedData = uris,
			) { (state, answers) ->
				val repo: ImagesRepository = mockk {
					coEvery { fetchImages() }.answers()
				}

				val tested = ImagesViewModel(repo)

				tested.state.value shouldBe state
			}
		}
	},
)
