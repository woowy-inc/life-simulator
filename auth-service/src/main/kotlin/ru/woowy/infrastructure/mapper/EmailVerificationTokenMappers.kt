package ru.woowy.infrastructure.mapper

import ru.woowy.domain.model.EmailVerificationKey
import ru.woowy.infrastructure.persistance.entity.EmailVerificationKeyEntity

internal fun EmailVerificationKeyEntity.asDomain() = EmailVerificationKey(
    key = this.key,
    user = this.user.asDomain(),
    expiresAt = this.expiresAt,
    used = this.used,
)

internal fun EmailVerificationKey.asEntity() = EmailVerificationKeyEntity(
    key = this.key,
    user = this.user.asEntity(),
    expiresAt = this.expiresAt,
    used = this.used,
)