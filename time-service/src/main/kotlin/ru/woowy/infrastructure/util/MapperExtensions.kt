package ru.woowy.infrastructure.util

import tools.jackson.module.kotlin.jacksonObjectMapper

fun Any.serialize(): String = jacksonObjectMapper().writeValueAsString(this)