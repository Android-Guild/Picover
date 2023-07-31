package com.intive.picover.common.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intive.picover.R
import com.intive.picover.main.theme.Typography

@Preview
@Composable
fun PicoverGenericError(
	message: String = stringResource(id = R.string.GenericErrorMessage),
	onRetryClick: (() -> Unit)? = null,
) {
	Column(
		modifier = Modifier
			.padding(32.dp)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Image(
			modifier = Modifier.padding(bottom = 16.dp),
			painter = painterResource(R.drawable.ic_generic_error),
			contentDescription = null,
		)
		Text(
			modifier = Modifier.padding(bottom = 8.dp),
			text = message,
			style = Typography.titleMedium,
			textAlign = TextAlign.Center,
		)
		onRetryClick?.let {
			TextButton(onClick = it) {
				Text(stringResource(id = R.string.GenericErrorButtonText).uppercase())
			}
		}
	}
}
