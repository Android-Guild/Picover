package com.intive.picover.parties.viewmodel

import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.parties.model.PartiesEvent
import com.intive.picover.parties.model.PartiesSideEffect
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.model.PartyRemote
import com.intive.picover.parties.model.toUI
import com.intive.picover.parties.repository.PartiesRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class PartiesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val dispatcher = UnconfinedTestDispatcher()
		val partiesRemote: List<PartyRemote> = mockk()
		val parties: List<Party> = mockk()
		val repository: PartiesRepository = mockk()
		lateinit var tested: PartiesViewModel

		beforeSpec {
			mockkStatic(List<PartyRemote>::toUI)
			every { partiesRemote.toUI() } returns parties
		}

		afterSpec {
			unmockkAll()
		}

		should("set state WHEN initialized according to fetch parties result") {
			listOf(
				emptyFlow<List<PartyRemote>>() to Loading,
				flowOf(partiesRemote) to Loaded(parties),
				flow<List<PartyRemote>> { throw Exception() } to Error,
			).forAll { (result, state) ->
				every { repository.parties() } returns result

				tested = PartiesViewModel(repository)

				tested.state.value shouldBe state
			}
		}

		should("start loading parties WHEN load parties event is emitted") {
			clearMocks(repository)

			tested.handleEvents(PartiesEvent.Load)

			coVerify { repository.parties() }
		}

		should("set navigate to party details side effect WHEN onPartyClick called") {
			runTest(dispatcher) {
				val deferredSideEffect = async {
					tested.sideEffect.first()
				}

				tested.onPartyClick(1)

				deferredSideEffect.await() shouldBe PartiesSideEffect.NavigateToPartyDetails(1)
			}
		}
	},
)
