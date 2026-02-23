package ru.woowy.infrastructure.extension

import org.springframework.security.crypto.keygen.KeyGenerators

fun generateSecureHexString(byteLength: Int = 32): String = KeyGenerators
    .secureRandom(byteLength)
    .generateKey()
    .joinToString("") { "%02x".format(it) }