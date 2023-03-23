package com.intive.picover

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class ExampleUnitTest : ShouldSpec({

	should("perform addition") {
		2 + 2 shouldBe 4
	}
})
