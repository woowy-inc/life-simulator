package ru.woowy.domain.model

internal abstract class NameCase {
    abstract val nominative: String
    abstract val genitive: String
    abstract val dative: String
    abstract val accusative: String
    abstract val ablative: String
    abstract val prepositional: String
    abstract val locative: String
}