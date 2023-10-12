package com.intive.picover.images.viewmodel

import android.net.Uri
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.mockkAnswer
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.images.repository.ImagesRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
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
			listOf(
				Loading to mockkAnswer<List<Uri>> { just(Awaits) },
				Error to mockkAnswer { throws(Throwable()) },
				Loaded(uris) to mockkAnswer { returns(uris) },
			).forAll { (state, answers) ->
				val repo: ImagesRepository = mockk {
					coEvery { fetchImages() }.answers()
				}

				val tested = ImagesViewModel(repo)

				tested.state.value shouldBe state
			}
		}
	},
)
