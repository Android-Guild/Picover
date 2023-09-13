package com.intive.picover.parties.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.intive.picover.R
import com.intive.picover.common.text.PicoverOutlinedTextField
import com.intive.picover.common.validator.ValidationStatus

@Composable
fun PartyBottomSheet(
	title: String,
	onTitleChange: (String) -> Unit,
	titleValidationStatus: ValidationStatus,
	description: String,
	onDescriptionChange: (String) -> Unit,
	descriptionValidationStatus: ValidationStatus,
	onButtonClick: () -> Unit,
) {
	val focusManager = LocalFocusManager.current
	Column(
		Modifier.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		PicoverOutlinedTextField(
			label = stringResource(R.string.PartyScreenAddPartyBottomSheetTitle),
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 16.dp),
			value = title,
			onValueChange = onTitleChange,
			isError = !titleValidationStatus.isValid(),
			errorText = stringResource(id = titleValidationStatus.errorMessageId),
			imeAction = ImeAction.Next,
			keyboardActions = KeyboardActions(
				onNext = {
					focusManager.moveFocus(FocusDirection.Down)
				},
			),
		)
		PicoverOutlinedTextField(
			label = stringResource(R.string.PartyScreenAddPartyBottomSheetDescription),
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 16.dp),
			value = description,
			onValueChange = onDescriptionChange,
			maxLines = 3,
			isError = !descriptionValidationStatus.isValid(),
			errorText = stringResource(id = descriptionValidationStatus.errorMessageId),
		)
		Button(
			modifier = Modifier,
			onClick = onButtonClick,
		) {
			Text(
				text = stringResource(R.string.SaveButton),
				color = Color.White,
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetValidPreview() {
	PartyBottomSheet(
		title = LoremIpsum(4).values.first(),
		onTitleChange = {},
		description = LoremIpsum(15).values.first(),
		onDescriptionChange = {},
		onButtonClick = {},
		titleValidationStatus = ValidationStatus.ValidText,
		descriptionValidationStatus = ValidationStatus.ValidText,
	)
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetInvalidPreview() {
	PartyBottomSheet(
		title = LoremIpsum(6).values.first(),
		onTitleChange = {},
		description = LoremIpsum(20).values.first(),
		onDescriptionChange = {},
		onButtonClick = {},
		titleValidationStatus = ValidationStatus.TooLongText,
		descriptionValidationStatus = ValidationStatus.TooLongText,
	)
}
