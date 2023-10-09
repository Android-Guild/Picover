package com.intive.picover.articles.viewmodel

import com.intive.picover.articles.repository.ArticlesRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.viewmodel.state.ViewModelState
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class ArticlesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())
		isolationMode = IsolationMode.InstancePerTest

		val articlesRepository: ArticlesRepository = mockk()
		val tested by lazy { ArticlesViewModel(articlesRepository) }

		should("return set initial state to loading") {
			tested.state.value shouldBe ViewModelState.Loading
		}

		should("return set state to error WHEN repository returns failure") {
			coEvery { articlesRepository.names() } returns Result.failure(Throwable())

			tested.state.value shouldBe ViewModelState.Error
		}

		should("return set state to loaded WHEN repository returns success") {
			val articleNames: List<String> = mockk()
			coEvery { articlesRepository.names() } returns Result.success(articleNames)

			tested.state.value shouldBe ViewModelState.Loaded(articleNames)
		}
	},
)
