package ru.woowy.application.util

import tools.jackson.module.kotlin.jsonMapper
import tools.jackson.module.kotlin.kotlinModule

private val mapper = jsonMapper { addModule(kotlinModule()) }

fun Any.serialize(): String = mapper.writeValueAsString(this)