package com.intive.picover.common.text

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

@Composable
fun PicoverOutlinedTextField(
	modifier: Modifier = Modifier,
	value: String,
	label: String,
	onValueChange: (String) -> Unit,
	maxLines: Int = 1,
	keyboardType: KeyboardType = KeyboardType.Text,
	imeAction: ImeAction = ImeAction.Done,
	keyboardActions: KeyboardActions = KeyboardActions(),
	errorText: String? = null,
	isEnabled: Boolean = true,
	trailingIcon: @Composable (() -> Unit)? = null,
) {
	OutlinedTextField(
		modifier = modifier,
		value = value,
		label = {
			Text(text = label)
		},
		onValueChange = onValueChange,
		maxLines = maxLines,
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = imeAction,
			keyboardType = keyboardType,
		),
		keyboardActions = keyboardActions,
		supportingText = {
			errorText?.let {
				Text(text = it)
			}
		},
		isError = errorText != null,
		enabled = isEnabled,
		trailingIcon = trailingIcon,
	)
}

@Preview(showBackground = true)
@Composable
private fun PicoverOutlinedTextFieldValidPreview() {
	PicoverOutlinedTextField(
		value = LoremIpsum(5).values.first(),
		label = "label",
		onValueChange = {},
	)
}

@Preview(showBackground = true)
@Composable
private fun PicoverOutlinedTextFieldInvalidPreview() {
	PicoverOutlinedTextField(
		value = LoremIpsum(5).values.first(),
		label = "label",
		onValueChange = {},
		errorText = "Error message",
	)
}
