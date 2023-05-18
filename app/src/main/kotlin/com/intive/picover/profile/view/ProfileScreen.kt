package com.intive.picover.profile.view

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.intive.picover.R

@Composable
fun ProfileScreen() {
	val context = LocalContext.current
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Button(
			onClick = {
				context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
			},
		) {
			Text(stringResource(R.string.OpenLicenses))
		}
	}
}
