package com.intive.picover.validators

import com.intive.picover.validators.ValidationStatus.BlankText
import com.intive.picover.validators.ValidationStatus.EmptyText
import com.intive.picover.validators.ValidationStatus.TooLongText
import com.intive.picover.validators.ValidationStatus.ValidText

class TextValidator private constructor(
	private val text: String,
	private val allowEmpty: Boolean,
	private val allowBlank: Boolean,
	private val maxLength: Int?,
) {

	fun validatingText() = when {
		!allowEmpty && text.isEmpty() -> EmptyText
		!allowBlank && text.isBlank() -> BlankText
		maxLength != null && text.length > maxLength -> TooLongText
		else -> ValidText
	}

	class Builder(private val text: String) {
		private var allowEmpty = true
		private var allowBlank = true
		private var maxLength: Int? = null

		fun allowEmpty(allow: Boolean) = apply { allowEmpty = allow }
		fun allowBlank(allow: Boolean) = apply { allowBlank = allow }
		fun maxLength(length: Int) = apply { maxLength = length }
		fun build() = TextValidator(text, allowEmpty, allowBlank, maxLength)
	}
}
