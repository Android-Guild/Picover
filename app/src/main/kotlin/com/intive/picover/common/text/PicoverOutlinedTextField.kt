package com.intive.picover.common.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.intive.picover.common.annotation.LightDarkPreview
import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.ValidationStatus
import com.intive.picover.main.theme.PicoverTheme

@Composable
fun PicoverOutlinedTextField(
	modifier: Modifier = Modifier,
	value: String,
	labelText: String,
	onValueChange: (String) -> Unit,
	maxLines: Int = 1,
	keyboardType: KeyboardType = KeyboardType.Text,
	imeAction: ImeAction = ImeAction.Done,
	keyboardActions: KeyboardActions = KeyboardActions(),
	isEnabled: Boolean = true,
	validator: TextValidator,
	onValidationStatusChange: (Boolean) -> Unit = {},
	trailingIcon: @Composable (() -> Unit)? = null,
) {
	var textValue by remember { mutableStateOf(value) }
	var validationStatus: ValidationStatus? by remember { mutableStateOf(null) }
	OutlinedTextField(
		modifier = modifier,
		value = value,
		label = {
			Text(text = labelText)
		},
		onValueChange = {
			textValue = it
			onValueChange(it)
			validationStatus = validator.validate(textValue).also { status ->
				onValidationStatusChange(status.errorMessageId == null)
			}
		},
		maxLines = maxLines,
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = imeAction,
			keyboardType = keyboardType,
		),
		keyboardActions = keyboardActions,
		supportingText = {
			validationStatus?.errorMessageId?.let {
				Text(
					modifier = Modifier.fillMaxWidth(),
					text = stringResource(id = it),
				)
			}
			Text(
				text = "${textValue.length} / ${validator.maxLength}",
				modifier = Modifier.fillMaxWidth(),
				textAlign = TextAlign.End,
			)
		},
		isError = validationStatus?.errorMessageId != null,
		enabled = isEnabled,
		trailingIcon = trailingIcon,
	)
}

@LightDarkPreview
@Composable
private fun PicoverOutlinedTextFieldPreview() {
	PicoverTheme {
		var description by remember { mutableStateOf(LoremIpsum(10).values.first()) }
		PicoverOutlinedTextField(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			value = description,
			labelText = "Description",
			onValueChange = { description = it },
			validator = TextValidator.Long,
			maxLines = 3,
		)
	}
}
