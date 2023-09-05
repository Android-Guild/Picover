package com.intive.picover.validators

import com.intive.picover.R
import com.intive.picover.validators.ValidationStatus.BlankText
import com.intive.picover.validators.ValidationStatus.EmptyText
import com.intive.picover.validators.ValidationStatus.TooLongText
import com.intive.picover.validators.ValidationStatus.ValidText
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class ValidationStatusTest : ShouldSpec(
	{
		should("be or not be valid and return specific message-id based on ValidationStatus") {
			listOf(
				ValidationStatusTestParam(
					validationStatus = ValidText,
					isValid = true,
					errorMessageResId = R.string.TextIsValid,
				),
				ValidationStatusTestParam(
					validationStatus = EmptyText,
					isValid = false,
					errorMessageResId = R.string.TextShouldNotBeEmpty,
				),
				ValidationStatusTestParam(
					validationStatus = BlankText,
					isValid = false,
					errorMessageResId = R.string.TextShouldNotBeBlank,
				),
				ValidationStatusTestParam(
					validationStatus = TooLongText,
					isValid = false,
					errorMessageResId = R.string.TextIsTooLong,
				),
			).forAll { param ->
				param.validationStatus.isValid() shouldBe param.isValid
				param.validationStatus.errorMessageId shouldBe param.errorMessageResId
			}
		}
	},
)

data class ValidationStatusTestParam(
	val validationStatus: ValidationStatus,
	val isValid: Boolean,
	val errorMessageResId: Int,
)
