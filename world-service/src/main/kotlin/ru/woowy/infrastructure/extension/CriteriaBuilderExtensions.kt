package ru.woowy.infrastructure.extension

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.From
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate

fun CriteriaBuilder.likeAny(
    pattern: String,
    vararg column: Expression<String>,
): Predicate = or(*column.map { like(lower(it), pattern) }.toTypedArray())

@Suppress("UNCHECKED_CAST")
inline fun <reified TEntity, reified TJoined> From<*, TEntity>.joinFetch(
    attribute: String,
    joinType: JoinType = JoinType.LEFT,
): Join<TEntity, TJoined> = fetch<TEntity, TJoined>(attribute, joinType) as Join<TEntity, TJoined>