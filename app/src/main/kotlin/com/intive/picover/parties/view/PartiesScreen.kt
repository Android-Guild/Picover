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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.intive.picover.common.validator.ValidationStatus
import com.intive.picover.main.theme.Typography
import com.intive.picover.parties.model.PartiesEvent
import com.intive.picover.parties.model.PartiesSideEffect
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.viewmodel.PartiesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartiesScreen(
	viewModel: PartiesViewModel,
	navController: NavHostController,
) {
	val state by viewModel.state
	val sheetState = rememberModalBottomSheetState()
	val coroutineScope = rememberCoroutineScope()
	LaunchedEffect(true) {
		viewModel.sideEffects.collectLatest { effect ->
			when (effect) {
				is PartiesSideEffect.NavigateToPartyDetails -> navController.navigate("partyDetails/${effect.partyId}")
			}
		}
	}
	when {
		state.isLoading() -> PicoverLoader(modifier = Modifier.fillMaxSize())
		state.isLoaded() -> LoadedContent(
			parties = state.data(),
			onPartyClick = viewModel::onPartyClick,
			title = viewModel.title,
			onTitleChange = { viewModel.updateTitle(it) },
			titleValidationStatus = viewModel.validateShortText(viewModel.title),
			description = viewModel.description,
			onDescriptionChange = { viewModel.updateDescription(it) },
			descriptionValidationStatus = viewModel.validateLongText(viewModel.description),
			isBottomSheetVisible = sheetState.isVisible,
			onFabClick = {
				coroutineScope.launch {
					sheetState.show()
				}
			},
			onBottomSheetDismissClick = {
				coroutineScope.launch {
					sheetState.hide()
				}
				viewModel.updateTitle("")
				viewModel.updateDescription("")
			},
		)

		state.isError() -> PicoverGenericError(onRetryClick = { viewModel.emitEvent(PartiesEvent.Load) })
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadedContent(
	parties: List<Party>,
	onPartyClick: (Int) -> Unit,
	title: String,
	onTitleChange: (String) -> Unit,
	titleValidationStatus: ValidationStatus,
	description: String,
	onDescriptionChange: (String) -> Unit,
	descriptionValidationStatus: ValidationStatus,
	isBottomSheetVisible: Boolean,
	onFabClick: () -> Unit,
	onBottomSheetDismissClick: () -> Unit,
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
		if (isBottomSheetVisible) {
			ModalBottomSheet(
				modifier = Modifier,
				onDismissRequest = onBottomSheetDismissClick,
			) {
				PartyBottomSheet(
					title = title,
					onTitleChange = onTitleChange,
					description = description,
					onDescriptionChange = onDescriptionChange,
					onButtonClick = { // TODO-KMA implement onClick action
					},
					titleValidationStatus = titleValidationStatus,
					descriptionValidationStatus = descriptionValidationStatus,
				)
			}
		}
	}
}

@Composable
private fun PartyTile(
	party: Party,
	onClick: (Int) -> Unit,
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
	val parties = (1..5).map {
		Party(
			id = it,
			title = "title$it",
			description = "description$it",
		)
	}
	LoadedContent(
		parties = parties,
		onPartyClick = {},
		title = "",
		onTitleChange = {},
		description = "",
		onDescriptionChange = {},
		titleValidationStatus = ValidationStatus.ValidText,
		descriptionValidationStatus = ValidationStatus.ValidText,
		isBottomSheetVisible = true,
		onFabClick = {},
		onBottomSheetDismissClick = {},
	)
}
