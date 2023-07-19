package com.intive.picover.common.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ToastPublisher @Inject constructor(
	@ApplicationContext private val context: Context,
) {

	fun show(@StringRes textId: Int) {
		Toast.makeText(context, textId, Toast.LENGTH_LONG).show()
	}
}
