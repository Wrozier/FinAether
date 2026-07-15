package com.tc.finaether

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform