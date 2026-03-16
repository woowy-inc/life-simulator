package ru.woowy.account.infrastructure.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.Currency

@Converter(autoApply = true)
class CurrencyConverter : AttributeConverter<Currency, String> {
    override fun convertToDatabaseColumn(attribute: Currency): String = attribute.currencyCode

    override fun convertToEntityAttribute(currencyCode: String): Currency = Currency.getInstance(currencyCode)
}