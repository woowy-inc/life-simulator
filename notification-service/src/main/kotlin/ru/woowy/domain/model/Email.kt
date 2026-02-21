package ru.woowy.domain.model

internal abstract class Email {
    abstract val to: String
    abstract val subject: String
    abstract val body: String
    abstract val isHtml: Boolean
}