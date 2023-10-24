package com.intive.ktlint.property

import com.intive.ktlint.base.PicoverRule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType

internal class UseExplicitTypeForMockkProperty : PicoverRule("property-explicit-mockk-type") {

	override fun beforeVisitChildNodes(
		node: ASTNode,
		autoCorrect: Boolean,
		emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
	) {
		if (node.psi is KtProperty &&
			node.isMockkCalleeExpression() &&
			node.hasExpressionTypeArguments()
		) {
			emit(node.startOffset, "Specify type explicitly for mockk property", false)
		}
	}

	private fun ASTNode.isMockkCalleeExpression() =
		psi.getChildOfType<KtCallExpression>()?.calleeExpression?.text == "mockk"

	private fun ASTNode.hasExpressionTypeArguments() =
		psi.getChildOfType<KtCallExpression>()?.typeArguments?.isNotEmpty() == true
}
