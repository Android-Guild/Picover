package com.intive.picover.common.toast

import android.content.Context
import android.widget.Toast
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify

class ToastPublisherTest : ShouldSpec(
	{

		val context: Context = mockk()
		val tested = ToastPublisher(context)

		beforeSpec {
			mockkStatic(Toast::class)
		}

		afterSpec {
			unmockkAll()
		}

		should("create and show toast with given textId WHEN show called") {
			val textId = 123455678
			every { Toast.makeText(any(), textId, any()).show() } just Runs

			tested.show(textId)

			verify { Toast.makeText(context, textId, Toast.LENGTH_LONG) }
		}
	},
)
