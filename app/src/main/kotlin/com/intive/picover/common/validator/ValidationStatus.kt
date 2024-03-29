package com.intive.picover.common.validator

import com.intive.picover.R

sealed class ValidationStatus(val errorMessageId: Int?) {
	data object EmptyText : ValidationStatus(R.string.TextShouldNotBeEmpty)
	data object BlankText : ValidationStatus(R.string.TextShouldNotBeBlank)
	data object TooLongText : ValidationStatus(R.string.TextIsTooLong)
	data object ValidText : ValidationStatus(null)
}
