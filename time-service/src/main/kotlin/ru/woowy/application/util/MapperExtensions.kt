package ru.woowy.application.util

import tools.jackson.module.kotlin.jacksonObjectMapper

fun Any.serialize(): String = jacksonObjectMapper().writeValueAsString(this)