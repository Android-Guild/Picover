package com.intive.picover.common.viewmodel.state

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.mockk

internal class ViewModelStateTest : ShouldSpec(
	{
		val data: Any = mockk()

		should("isLoading AND isError AND isLoaded AND return values based on view model state") {
			listOf(
				ViewModelStateParams(
					state = ViewModelState.Loading,
					isLoading = true,
					isError = false,
					isLoaded = false,
				),
				ViewModelStateParams(
					state = ViewModelState.Error,
					isLoading = false,
					isError = true,
					isLoaded = false,
				),
				ViewModelStateParams(
					state = ViewModelState.Loaded(data),
					isLoading = false,
					isError = false,
					isLoaded = true,
				),
			).forAll { (viewModelState, isLoading, isError, isLoaded) ->
				assertSoftly(viewModelState) {
					isLoading() shouldBe isLoading
					isError() shouldBe isError
					isLoaded() shouldBe isLoaded
				}
			}
		}

		should("throw ClassCastException WHEN data called AND view model state is NOT loaded") {
			listOf(
				ViewModelState.Loading,
				ViewModelState.Error,
			).forAll { viewModelState ->
				shouldThrowExactly<ClassCastException> {
					viewModelState.data()
				}
			}
		}

		should("return data WHEN data called AND view model state is loaded") {
			ViewModelState.Loaded(data).data() shouldBe data
		}
	},
) {
	private data class ViewModelStateParams(
		val state: ViewModelState<Any>,
		val isLoading: Boolean,
		val isError: Boolean,
		val isLoaded: Boolean,
	)
}
