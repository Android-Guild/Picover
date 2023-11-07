package com.intive.picover.articles.viewmodel

import com.intive.picover.articles.repository.ArticlesRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.mockkAnswer
import com.intive.picover.common.viewmodel.state.ViewModelState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk

class ArticlesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val articlesRepository: ArticlesRepository = mockk()

		should("set state WHEN depending on repo result") {
			val articleNames: List<String> = mockk()
			listOf(
				ViewModelState.Loading to mockkAnswer<Result<List<String>>> { just(Awaits) },
				ViewModelState.Error to mockkAnswer { returns(Result.failure(Throwable())) },
				ViewModelState.Loaded(articleNames) to mockkAnswer { returns(Result.success(articleNames)) },
			).forAll { (state, answers) ->
				coEvery { articlesRepository.names() }.answers()

				val tested = ArticlesViewModel(articlesRepository)

				tested.state.value shouldBe state
			}
		}
	},
)
