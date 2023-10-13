package com.intive.picover.articles.viewmodel

import com.intive.picover.articles.repository.ArticlesRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.testState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk

class ArticlesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		should("set state WHEN repository called") {
			val articleNames: List<String> = mockk()
			testState<Result<List<String>>, List<String>>(
				loadingAnswer = { just(Awaits) },
				errorAnswer = { returns(Result.failure(Throwable())) },
				loadedAnswer = { returns(Result.success(articleNames)) },
				loadedData = articleNames,
			) { (state, answers) ->
				val articlesRepository: ArticlesRepository = mockk {
					coEvery { names() }.answers()
				}

				val tested = ArticlesViewModel(articlesRepository)

				tested.state.value shouldBe state
			}
		}
	},
)
