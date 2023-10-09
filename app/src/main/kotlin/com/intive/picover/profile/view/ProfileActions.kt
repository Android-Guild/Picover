package com.intive.picover.profile.view

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.intive.picover.R
import com.intive.picover.profile.model.ProfileActionsItem

@Composable
fun ProfileActions(onLogoutClick: () -> Unit, onDeleteAccountCLick: () -> Unit) {
	val context = LocalContext.current
	val items = listOf(
		ProfileActionsItem.Licenses(
			onClick = {
				context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
			},
		),
		ProfileActionsItem.Logout(onClick = onLogoutClick),
		ProfileActionsItem.DeleteAccount(onClick = onDeleteAccountCLick),
		ProfileActionsItem.GitHub(
			onClick = {
				CustomTabsIntent
					.Builder()
					.build()
					.launchUrl(context, Uri.parse(context.getString(R.string.GitHub)))
			},
		),
	)
	LazyColumn {
		items(items) {
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.size(24.dp),
						painter = painterResource(id = it.iconId),
						contentDescription = stringResource(id = it.textId),
					)
				},
				label = {
					Text(text = stringResource(id = it.textId))
				},
				onClick = it.onClick,
				selected = false,
			)
		}
	}
}
