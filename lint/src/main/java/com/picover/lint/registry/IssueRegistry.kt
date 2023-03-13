package com.picover.lint.registry

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.picover.lint.detector.StringResourceCapitalizationDetector

class IssueRegistry : IssueRegistry() {

	override val api = CURRENT_API

	override val vendor: Vendor = Vendor(
		vendorName = "Szczecin Android Guild Developers",
	)

	override val issues = listOf(
		StringResourceCapitalizationDetector.ISSUE
	)
}
