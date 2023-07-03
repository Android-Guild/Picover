package com.intive.picover.profile.model

import android.net.Uri

data class Profile(
	val photo: Uri?,
	val name: String,
	val email: String,
)
