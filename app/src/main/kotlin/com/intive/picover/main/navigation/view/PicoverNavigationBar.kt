package com.intive.picover.main.navigation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.intive.picover.main.navigation.model.NavigationItem
import com.intive.picover.main.view.navigateWithSingleTop

@Composable
fun PicoverNavigationBar(navController: NavHostController) {
	val backStackEntry = navController.currentBackStackEntryAsState()
	NavigationBar(modifier = Modifier.fillMaxWidth()) {
		NavigationItem.entries.forEach { item ->
			NavigationBarItem(
				selected = item.route == backStackEntry.value?.destination?.route,
				onClick = { navController.navigateWithSingleTop(item) },
				icon = {
					Icon(
						imageVector = item.icon,
						contentDescription = stringResource(id = item.labelResId),
					)
				},
				label = {
					Text(text = stringResource(id = item.labelResId))
				},
			)
		}
	}
}
