package com.intive.picover.common.converters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import com.intive.picover.common.sdk.AndroidSdkVersion
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import java.io.ByteArrayOutputStream

class BitmapConverterTest : ShouldSpec(
	{
		isolationMode = IsolationMode.InstancePerTest

		val context: Context = mockk()
		val uri: Uri = mockk()
		val qualityPercent = 50
		val compressFormat = Bitmap.CompressFormat.JPEG
		val byteArrayOutputStreamCapture = slot<ByteArrayOutputStream>()
		val bitmap: Bitmap = mockk {
			every { compress(compressFormat, qualityPercent, capture(byteArrayOutputStreamCapture)) } returns true
		}
		val tested = BitmapConverter(context)

		beforeSpec {
			mockkObject(AndroidSdkVersion)
			mockkStatic(MediaStore.Images.Media::class, ImageDecoder::class)
		}

		afterSpec {
			unmockkAll()
		}

		should("called bitmap.compress WHEN convertBitmapToBytes invoked") {
			every { AndroidSdkVersion.isVersionUnderPieSdk() } returns true
			every { MediaStore.Images.Media.getBitmap(context.contentResolver, uri) } returns bitmap

			tested.convertBitmapToBytes(uri)

			verify { bitmap.compress(compressFormat, qualityPercent, byteArrayOutputStreamCapture.captured) }
		}

		should("called Media.getBitmap WHEN if Android SDK is < Pie version") {
			every { AndroidSdkVersion.isVersionUnderPieSdk() } returns true
			every { MediaStore.Images.Media.getBitmap(context.contentResolver, uri) } returns bitmap

			tested.convertBitmapToBytes(uri)

			verify { MediaStore.Images.Media.getBitmap(context.contentResolver, uri) }
		}

		should("called Media.getBitmap WHEN if Android SDK is >= Pie version") {
			every { AndroidSdkVersion.isVersionUnderPieSdk() } returns false
			every { ImageDecoder.createSource(context.contentResolver, uri) } returns mockk {
				every { ImageDecoder.decodeBitmap(this@mockk) } returns bitmap
			}

			tested.convertBitmapToBytes(uri)

			verify { ImageDecoder.decodeBitmap(any()) }
		}

		should("return ByteArrayOutputStream WHEN convertBitmapToBytes called") {
			every { AndroidSdkVersion.isVersionUnderPieSdk() } returns true
			every { MediaStore.Images.Media.getBitmap(context.contentResolver, uri) } returns bitmap

			tested.convertBitmapToBytes(uri) shouldBe byteArrayOutputStreamCapture.captured
		}
	},
)
