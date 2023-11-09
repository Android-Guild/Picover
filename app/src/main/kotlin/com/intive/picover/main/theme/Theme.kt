package com.intive.picover.main.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import com.intive.picover.R
import com.skydoves.landscapist.components.LocalImageComponent
import com.skydoves.landscapist.components.imageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

private val DarkColorScheme = darkColorScheme(
	primary = Purple80,
	secondary = PurpleGrey80,
	tertiary = Pink80,
)

private val LightColorScheme = lightColorScheme(
	primary = Purple40,
	secondary = PurpleGrey40,
	tertiary = Pink40,
)

@Composable
fun PicoverTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	dynamicColor: Boolean = true,
	content: @Composable () -> Unit,
) {
	val colorScheme = when {
		dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
			val context = LocalContext.current
			if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
		}

		darkTheme -> DarkColorScheme
		else -> LightColorScheme
	}
	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			with(view.context as Activity) {
				window.navigationBarColor = colorScheme.primary.copy(alpha = 0.08f).compositeOver(colorScheme.surface.copy()).toArgb()
				window.statusBarColor = colorScheme.background.toArgb()
				WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
				WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
			}
		}
	}
	val imageComponent = imageComponent {
		+PlaceholderPlugin.Failure(painterResource(R.drawable.ic_avatar_placeholder))
	}
	CompositionLocalProvider(LocalImageComponent provides imageComponent) {
		MaterialTheme(
			colorScheme = colorScheme,
			typography = Typography,
			content = content,
		)
	}
}
