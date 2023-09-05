package com.intive.picover.validators

import com.intive.picover.R

sealed class ValidationStatus(val errorMessageId: Int) {
	object EmptyText : ValidationStatus(R.string.TextShouldNotBeEmpty)
	object BlankText : ValidationStatus(R.string.TextShouldNotBeBlank)
	object TooLongText : ValidationStatus(R.string.TextIsTooLong)
	object ValidText : ValidationStatus(R.string.TextIsValid)

	fun isValid() =
		this is ValidText
}
