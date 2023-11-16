package com.intive.picover.common.validator

import androidx.annotation.VisibleForTesting
import com.intive.picover.common.validator.ValidationStatus.BlankText
import com.intive.picover.common.validator.ValidationStatus.EmptyText
import com.intive.picover.common.validator.ValidationStatus.TooLongText
import com.intive.picover.common.validator.ValidationStatus.ValidText

class TextValidator @VisibleForTesting constructor(
	private val allowEmpty: Boolean,
	private val allowBlank: Boolean,
	val maxLength: Int?,
) {

	fun validate(text: String) =
		when {
			!allowEmpty && text.isEmpty() -> EmptyText
			!allowBlank && text.isBlank() -> BlankText
			maxLength != null && text.length > maxLength -> TooLongText
			else -> ValidText
		}

	companion object {
		val Short = TextValidator(allowEmpty = false, allowBlank = false, maxLength = 20)
		val Long = TextValidator(allowEmpty = false, allowBlank = false, maxLength = 80)
	}
}
