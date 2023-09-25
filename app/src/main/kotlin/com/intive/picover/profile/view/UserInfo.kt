package com.intive.picover.profile.view

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.intive.picover.R
import com.intive.picover.common.animations.ShimmerBrush
import com.intive.picover.common.annotation.LightDarkPreview
import com.intive.picover.main.theme.PicoverTheme
import com.intive.picover.main.theme.Typography
import com.intive.picover.profile.model.Profile
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun UserInfo(
	profile: Profile,
	onEditPhotoClick: () -> Unit,
	onEditNameClick: () -> Unit,
	editButtonsEnabled: Boolean = true,
	showShimmer: Boolean = false,
) {
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Box(modifier = Modifier.size(120.dp)) {
			if (!showShimmer) {
				UserAvatar(profile.photo)
			} else {
				val brush = ShimmerBrush(
					targetValue = 1300f,
					showShimmer = true,
				)
				Canvas(
					modifier = Modifier.size(120.dp),
					onDraw = {
						drawRect(
							brush = brush,
							size = size,
						)
					},
				)
			}
			FilledIconButton(
				modifier = Modifier.align(Alignment.BottomEnd),
				shape = CircleShape,
				onClick = onEditPhotoClick,
				enabled = editButtonsEnabled,
			) {
				Icon(
					modifier = Modifier.size(24.dp),
					imageVector = Icons.Rounded.PhotoCamera,
					contentDescription = null,
				)
			}
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Spacer(modifier = Modifier.width(48.dp))
			Text(
				modifier = Modifier.weight(1f),
				text = profile.name,
				style = Typography.titleLarge,
				textAlign = TextAlign.Center,
			)
			IconButton(
				onClick = onEditNameClick,
				enabled = editButtonsEnabled,
			) {
				Icon(
					modifier = Modifier.size(24.dp),
					imageVector = Icons.Rounded.Edit,
					contentDescription = null,
				)
			}
		}
		Text(
			text = profile.email,
			color = Color.Gray,
			style = Typography.titleSmall,
		)
		Divider(modifier = Modifier.padding(all = 16.dp))
	}
}

@Composable
private fun UserAvatar(imageUri: Uri?) {
	CoilImage(
		modifier = Modifier
			.size(120.dp)
			.clip(CircleShape),
		imageModel = { imageUri },
		imageOptions = ImageOptions(
			alignment = Alignment.Center,
			contentDescription = null,
			contentScale = ContentScale.Crop,
		),
		loading = {
			CircularProgressIndicator()
		},
		failure = {
			Image(
				modifier = Modifier
					.size(120.dp)
					.clip(CircleShape),
				painter = painterResource(id = R.drawable.ic_avatar_placeholder),
				contentDescription = null,
				alignment = Alignment.Center,
				contentScale = ContentScale.Crop,
			)
		},
	)
}

@LightDarkPreview
@Composable
private fun UserInfoPreview() {
	PicoverTheme {
		UserInfo(Profile(photo = Uri.parse("photo"), name = "test", email = "test@aa.bb"), {}, {})
	}
}
