package ru.woowy.domain.model

abstract class EmailBody {
    abstract val templateName: String
    abstract val variables: Map<String, Any>
}