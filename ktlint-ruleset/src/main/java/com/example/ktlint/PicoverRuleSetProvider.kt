package com.example.ktlint

import com.pinterest.ktlint.core.RuleProvider
import com.pinterest.ktlint.core.RuleSetProviderV2

class PicoverRuleSetProvider : RuleSetProviderV2(
    id = "picover-rules",
    about = About(
        maintainer = "Android Guild - Szczecin",
        description = "Custom ktlint rules used in Picover app",
        license = null,
        repositoryUrl = null,
        issueTrackerUrl = null
    )
) {

    override fun getRuleProviders() = setOf<RuleProvider>()
}
