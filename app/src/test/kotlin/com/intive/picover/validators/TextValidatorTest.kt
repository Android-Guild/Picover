package com.intive.picover.validators

import com.intive.picover.R
import com.intive.picover.common.validator.ValidationStatus
import com.intive.picover.common.validator.ValidationStatus.BlankText
import com.intive.picover.common.validator.ValidationStatus.EmptyText
import com.intive.picover.common.validator.ValidationStatus.TooLongText
import com.intive.picover.common.validator.ValidationStatus.ValidText
import com.intive.picover.common.validator.textValidator
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class TextValidatorTest : ShouldSpec(
	{
		should("be validated based on specific conditions") {
			listOf(
				TextValidatorTestParam(
					text = "Jack Smith",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					isValid = true,
					validationStatus = ValidText,
					errorMessageResId = R.string.TextIsValid,
				),
				TextValidatorTestParam(
					text = "0123456789",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					isValid = true,
					validationStatus = ValidText,
					errorMessageResId = R.string.TextIsValid,
				),
				TextValidatorTestParam(
					text = "012345678910",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					isValid = false,
					validationStatus = TooLongText,
					errorMessageResId = R.string.TextIsTooLong,
				),
				TextValidatorTestParam(
					text = "",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					isValid = false,
					validationStatus = EmptyText,
					errorMessageResId = R.string.TextShouldNotBeEmpty,
				),
				TextValidatorTestParam(
					text = "   ",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					isValid = false,
					validationStatus = BlankText,
					errorMessageResId = R.string.TextShouldNotBeBlank,
				),
				TextValidatorTestParam(
					text = "",
					allowEmpty = true,
					allowBlank = true,
					maxLength = 10,
					isValid = true,
					validationStatus = ValidText,
					errorMessageResId = R.string.TextIsValid,
				),
				TextValidatorTestParam(
					text = "   ",
					allowEmpty = false,
					allowBlank = true,
					maxLength = 10,
					isValid = true,
					validationStatus = ValidText,
					errorMessageResId = R.string.TextIsValid,
				),
			).forAll { param ->
				val tested = textValidator {
					allowEmpty = param.allowEmpty
					allowBlank = param.allowBlank
					maxLength = param.maxLength
				}

				tested.validate(param.text) shouldBe param.validationStatus
				tested.validate(param.text).isValid() shouldBe param.isValid
				tested.validate(param.text).errorMessageId shouldBe param.errorMessageResId
			}
		}
	},
)

data class TextValidatorTestParam(
	val text: String,
	val allowEmpty: Boolean,
	val allowBlank: Boolean,
	val maxLength: Int,
	val isValid: Boolean,
	val validationStatus: ValidationStatus,
	val errorMessageResId: Int,
)
