package ru.woowy.domain.model

abstract class Email {
    abstract val to: String
    abstract val subject: String
    abstract val body: String
    abstract val isHtml: Boolean
}