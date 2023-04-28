package com.picover.lint.detector

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.android.tools.lint.detector.api.XmlScannerConstants
import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Element

class StringResourceCapitalizationDetector : Detector(), XmlScanner {

	override fun appliesTo(folderType: ResourceFolderType) =
		folderType == ResourceFolderType.VALUES

	override fun getApplicableElements() =
		listOf(SdkConstants.TAG_STRING)

	override fun visitElement(context: XmlContext, element: Element) {
		val attribute = element.getAttributeNode(SdkConstants.ATTR_NAME)
		if (attribute.value.first().isLowerCase()) {
			context.report(ISSUE, context.getValueLocation(attribute), "Use PascalCase")
		}
	}

	companion object {

		val ISSUE = Issue.create(
			id = "StringResourceCapitalization",
			briefDescription = "Use PascalCase capitalization",
			explanation = "String id should be capitalized in PascalCase",
			category = Category.CORRECTNESS,
			severity = Severity.ERROR,
			implementation = Implementation(StringResourceCapitalizationDetector::class.java, Scope.RESOURCE_FILE_SCOPE),
			androidSpecific = true
		)
	}
}
