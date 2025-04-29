package com.gibson.fobicx

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform