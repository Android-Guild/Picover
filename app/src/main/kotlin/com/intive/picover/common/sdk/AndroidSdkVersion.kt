package com.intive.picover.common.sdk

import android.os.Build

object AndroidSdkVersion {

	fun isVersionUnderPieSdk() =
		Build.VERSION.SDK_INT < Build.VERSION_CODES.P
}
