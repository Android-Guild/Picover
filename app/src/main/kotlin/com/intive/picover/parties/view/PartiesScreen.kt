package com.intive.picover.parties.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.intive.picover.R
import com.intive.picover.common.error.PicoverGenericError
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.common.viewmodel.state.MVIState
import com.intive.picover.main.theme.Typography
import com.intive.picover.parties.model.PartiesEvent
import com.intive.picover.parties.model.PartiesSideEffect.NavigateToAddParty
import com.intive.picover.parties.model.PartiesSideEffect.NavigateToPartyDetails
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.viewmodel.PartiesViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PartiesScreen(
	viewModel: PartiesViewModel,
	navController: NavHostController,
) {
	val state by viewModel.state
	LaunchedEffect(true) {
		viewModel.sideEffects.collectLatest { effect ->
			when (effect) {
				is NavigateToPartyDetails -> navController.navigate("partyDetails/${effect.partyId}")
				is NavigateToAddParty -> navController.navigate("parties/addParty")
			}
		}
	}
	when (state.type) {
		MVIState.Type.LOADING -> PicoverLoader(modifier = Modifier.fillMaxSize())
		MVIState.Type.LOADED -> LoadedContent(
			parties = state.parties,
			onPartyClick = viewModel::onPartyClick,
			onFabClick = viewModel::onAddPartyClick,
		)

		MVIState.Type.ERROR -> PicoverGenericError(onRetryClick = { viewModel.emitEvent(PartiesEvent.Load) })
	}
}

@Composable
private fun LoadedContent(
	parties: List<Party>,
	onPartyClick: (String) -> Unit,
	onFabClick: () -> Unit,
) {
	Box(
		modifier = Modifier
			.fillMaxSize(),
	) {
		LazyColumn(
			modifier = Modifier.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
		) {
			items(parties) {
				PartyTile(
					party = it,
					onClick = onPartyClick,
				)
			}
		}
		FloatingActionButton(
			modifier = Modifier
				.padding(12.dp)
				.align(Alignment.BottomEnd),
			onClick = onFabClick,
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			contentColor = MaterialTheme.colorScheme.secondary,
		) {
			Icon(Icons.Filled.Add, stringResource(id = R.string.PartyScreenFabAddIcon))
		}
	}
}

@Composable
private fun PartyTile(
	party: Party,
	onClick: (String) -> Unit,
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable {
				onClick(party.id)
			},
	) {
		Column(modifier = Modifier.padding(16.dp)) {
			Text(
				text = party.title,
				style = Typography.titleMedium,
			)
			Text(
				modifier = Modifier.padding(top = 16.dp),
				text = party.description,
				maxLines = 3,
				overflow = TextOverflow.Ellipsis,
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun PartyScreenLoadedPreview() {
	val parties = (1..5)
		.map {
			Party(
				id = it.toString(),
				title = "title$it",
				description = "description$it",
			)
		}
	LoadedContent(
		parties = parties,
		onPartyClick = {},
		onFabClick = {},
	)
}
