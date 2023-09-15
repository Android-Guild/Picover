package com.intive.picover.validators

import com.intive.picover.R

sealed class ValidationStatus(val errorMessageId: Int) {
	data object EmptyText : ValidationStatus(R.string.TextShouldNotBeEmpty)
	data object BlankText : ValidationStatus(R.string.TextShouldNotBeBlank)
	data object TooLongText : ValidationStatus(R.string.TextIsTooLong)
	data object ValidText : ValidationStatus(R.string.TextIsValid)

	fun isValid() =
		this is ValidText
}
