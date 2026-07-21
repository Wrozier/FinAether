package com.tc.finaether.util

import com.tc.finaether.ui.screens.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}