package com.intive.ktlint.base

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId

internal abstract class PicoverRule(id: String) : Rule(RuleId("picover:$id"), About())
