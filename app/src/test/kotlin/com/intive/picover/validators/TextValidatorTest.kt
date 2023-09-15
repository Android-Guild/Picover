package com.intive.picover.validators

import com.intive.picover.R
import com.intive.picover.validators.ValidationStatus.BlankText
import com.intive.picover.validators.ValidationStatus.EmptyText
import com.intive.picover.validators.ValidationStatus.TooLongText
import com.intive.picover.validators.ValidationStatus.ValidText
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
				val tested = TextValidator.Builder()
					.allowEmpty(param.allowEmpty)
					.allowBlank(param.allowBlank)
					.maxLength(param.maxLength)
					.build()

				tested.validatingText(param.text) shouldBe param.validationStatus
				tested.validatingText(param.text).isValid() shouldBe param.isValid
				tested.validatingText(param.text).errorMessageId shouldBe param.errorMessageResId
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
