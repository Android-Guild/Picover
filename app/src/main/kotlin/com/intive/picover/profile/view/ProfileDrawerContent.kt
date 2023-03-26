package com.intive.picover.profile.view

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.intive.picover.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDrawerContent() {
	ModalDrawerSheet {
		Box(
			modifier = Modifier.fillMaxSize(),
		) {
			val context = LocalContext.current
			ClickableText(
				text = AnnotatedString(stringResource(R.string.Profile)),
				modifier = Modifier.align(Alignment.Center),
				onClick = {
					startActivity(context, Intent(context, OssLicensesMenuActivity::class.java), null)
				},
			)
		}
	}
}
