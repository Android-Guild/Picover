package com.intive.picover.parties.repository

import com.intive.picover.parties.model.Party
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockedPartiesRepository @Inject constructor() {

	private val parties = listOf(
		Party(id = 1, title = "title1", description = "description1"),
		Party(id = 2, title = "title2", description = "description2"),
		Party(id = 3, title = "title3", description = "description3"),
		Party(id = 4, title = "title4", description = "description4"),
		Party(id = 5, title = "title5", description = "description5"),
	)

	fun getParties() = parties

	fun getPartyById(id: Int) = parties.find { it.id == id }!!
}
