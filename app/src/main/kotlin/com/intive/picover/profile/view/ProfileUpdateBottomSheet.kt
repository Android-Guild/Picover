package com.intive.picover.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.intive.picover.R
import com.intive.picover.common.bottomsheet.PicoverModalBottomSheet
import com.intive.picover.common.text.PicoverOutlinedTextField
import com.intive.picover.common.validator.TextValidator

@Composable
fun ProfileUpdateBottomSheet(
	username: String,
	onSaveClick: () -> Unit,
	onClose: () -> Unit,
	onUsernameChange: (String) -> Unit,
) {
	var isUserNameValid by remember { mutableStateOf(true) }
	PicoverModalBottomSheet(onDismissRequest = onClose) { bottomSheetModifier ->
		ProfileUpdateContent(
			modifier = bottomSheetModifier,
			onClose = onClose,
			onSaveClick = onSaveClick,
			username = username,
			onUsernameChange = onUsernameChange,
			isUserNameValid = isUserNameValid,
			onValidationStatusChange = { isUserNameValid = it },
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUpdateContent(
	modifier: Modifier,
	onClose: () -> Unit,
	onSaveClick: () -> Unit,
	username: String,
	onUsernameChange: (String) -> Unit,
	isUserNameValid: Boolean,
	onValidationStatusChange: (Boolean) -> Unit,
) {
	Column(
		modifier = modifier,
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
						enabled = isUserNameValid,
					) {
						Icon(
							imageVector = Icons.Rounded.Check,
							contentDescription = null,
						)
					}
				},
			)
		}
		PicoverOutlinedTextField(
			modifier = Modifier.fillMaxWidth(),
			value = username,
			onValueChange = onUsernameChange,
			validator = TextValidator.Short,
			labelText = stringResource(id = R.string.UserName),
			onValidationStatusChange = onValidationStatusChange,
		)
	}
}
