package com.intive.picover.shared

interface Platform {
	val name: String
}

expect fun getPlatform(): Platform
