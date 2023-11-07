package com.intive.picover.validators

import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.ValidationStatus
import com.intive.picover.common.validator.ValidationStatus.BlankText
import com.intive.picover.common.validator.ValidationStatus.EmptyText
import com.intive.picover.common.validator.ValidationStatus.TooLongText
import com.intive.picover.common.validator.ValidationStatus.ValidText
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class TextValidatorTest : ShouldSpec(
	{
		should("be validated based on specific conditions") {
			listOf(
				TextValidatorTestParam(
					textToValidate = "Jack Smith",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					expectedStatus = ValidText,
				),
				TextValidatorTestParam(
					textToValidate = "0123456789",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					expectedStatus = ValidText,
				),
				TextValidatorTestParam(
					textToValidate = "012345678910",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					expectedStatus = TooLongText,
				),
				TextValidatorTestParam(
					textToValidate = "",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					expectedStatus = EmptyText,
				),
				TextValidatorTestParam(
					textToValidate = "   ",
					allowEmpty = false,
					allowBlank = false,
					maxLength = 10,
					expectedStatus = BlankText,
				),
				TextValidatorTestParam(
					textToValidate = "",
					allowEmpty = true,
					allowBlank = true,
					maxLength = 10,
					expectedStatus = ValidText,
				),
				TextValidatorTestParam(
					textToValidate = "   ",
					allowEmpty = false,
					allowBlank = true,
					maxLength = 10,
					expectedStatus = ValidText,
				),
			).forAll { param ->
				val tested = TextValidator(
					allowEmpty = param.allowEmpty,
					allowBlank = param.allowBlank,
					maxLength = param.maxLength,
				)

				tested.validate(param.textToValidate) shouldBe param.expectedStatus
			}
		}
	},
)

data class TextValidatorTestParam(
	val textToValidate: String,
	val allowEmpty: Boolean,
	val allowBlank: Boolean,
	val maxLength: Int,
	val expectedStatus: ValidationStatus,
)
