package com.intive.picover.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.intive.picover.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUpdateBottomSheet(
	username: String,
	onSaveClick: () -> Unit,
	onClose: () -> Unit,
	onUsernameChange: (String) -> Unit,
	usernameErrorMessageId: Int?,
) {
	ModalBottomSheet(
		modifier = Modifier.padding(bottom = 56.dp),
		onDismissRequest = onClose,
	) {
		Row(horizontalArrangement = Arrangement.SpaceAround) {
			CenterAlignedTopAppBar(
				navigationIcon = {
					IconButton(
						onClick = onClose,
					) {
						Icon(
							imageVector = Icons.Rounded.Close,
							contentDescription = null,
						)
					}
				},
				title = { Text(stringResource(id = R.string.EditUserData)) },
				actions = {
					IconButton(
						onClick = {
							onSaveClick()
							onClose()
						},
						enabled = usernameErrorMessageId == null,
					) {
						Icon(
							imageVector = Icons.Rounded.Check,
							contentDescription = null,
						)
					}
				},
			)
		}
		Column(
			modifier = Modifier
				.padding(start = 20.dp, end = 20.dp, bottom = 42.dp)
				.fillMaxWidth(),
		) {
			TextField(
				modifier = Modifier.fillMaxWidth(),
				value = username,
				onValueChange = {
					onUsernameChange(it)
				},
				supportingText = {
					usernameErrorMessageId?.let {
						Text(
							modifier = Modifier.fillMaxWidth(),
							text = stringResource(id = it),
						)
					}
					Text(
						text = "${username.length} / 20",
						modifier = Modifier.fillMaxWidth(),
						textAlign = TextAlign.End,
					)
				},
				isError = usernameErrorMessageId != null,
				label = { Text(text = stringResource(id = R.string.UserName)) },
				maxLines = 1,
			)
		}
	}
}
