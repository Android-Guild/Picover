package com.intive.picover.common.validator

import com.intive.picover.common.validator.ValidationStatus.BlankText
import com.intive.picover.common.validator.ValidationStatus.EmptyText
import com.intive.picover.common.validator.ValidationStatus.TooLongText
import com.intive.picover.common.validator.ValidationStatus.ValidText

class TextValidator private constructor(
	private val allowEmpty: Boolean,
	private val allowBlank: Boolean,
	private val maxLength: Int?,
) {

	fun validatingText(text: String) =
		when {
			!allowEmpty && text.isEmpty() -> EmptyText
			!allowBlank && text.isBlank() -> BlankText
			maxLength != null && text.length > maxLength -> TooLongText
			else -> ValidText
		}

	class Builder {
		var allowEmpty = true
		var allowBlank = true
		var maxLength: Int? = null

		fun build() =
			TextValidator(allowEmpty, allowBlank, maxLength)
	}
}

inline fun textValidator(block: TextValidator.Builder.() -> Unit) =
	TextValidator.Builder()
		.apply(block)
		.build()
