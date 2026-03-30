package ru.woowy.infrastructure.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ru.woowy.model.Percentage
import java.math.BigDecimal

object PercentageSerializer : KSerializer<Percentage> {
    override val descriptor = PrimitiveSerialDescriptor("Percentage", STRING)

    override fun serialize(
        encoder: Encoder,
        value: Percentage,
    ) = encoder.encodeString(value.value.toPlainString())

    override fun deserialize(decoder: Decoder): Percentage = Percentage.of(BigDecimal(decoder.decodeString()))
}