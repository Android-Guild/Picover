package com.intive.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

class PicoverRuleSetProvider : RuleSetProviderV3(RuleSetId("picover-rules")) {

	override fun getRuleProviders() = setOf<RuleProvider>()
}
