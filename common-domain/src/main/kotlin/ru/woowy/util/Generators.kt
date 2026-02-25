package ru.woowy.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.UUID
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Instant

fun randomString(length: Int = 8): String {
    val chars = ('a'..'z') + ('A'..'Z')
    return (1..length).map { chars.random() }.joinToString("")
}

fun randomEmail(localLength: Int = 8): String = "${randomString(localLength)}@${randomString(5)}.com"

fun randomUsername(length: Int = 8): String = randomString(length).lowercase()

fun randomPassword(length: Int = 12): String {
    val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9') + listOf('!', '@', '#', '$')
    return (1..length).map { chars.random() }.joinToString("")
}

fun randomInt(
    min: Int = 0,
    max: Int = 1000,
): Int = Random.nextInt(min, max)

fun randomLong(
    min: Long = 0L,
    max: Long = 100_000L,
): Long = Random.nextLong(min, max)

fun randomDouble(
    min: Double = 0.0,
    max: Double = 1000.0,
    precision: Int = 2,
): Double {
    val raw = Random.nextDouble(min, max)
    return "%.${precision}f".format(raw).toDouble()
}

fun randomBoolean(): Boolean = Random.nextBoolean()

fun randomInstant(
    from: Instant = Instant.parse("2000-01-01T00:00:00Z"),
    to: Instant = Clock.System.now(),
): Instant {
    val range = to.toEpochMilliseconds() - from.toEpochMilliseconds()
    return Instant.fromEpochMilliseconds(from.toEpochMilliseconds() + Random.nextLong(0, range))
}

fun randomLocalDate(
    from: LocalDate = LocalDate(2000, 1, 1),
    to: LocalDate = Clock.System.todayIn(TimeZone.UTC),
): LocalDate {
    val fromDay = from.toEpochDays()
    val toDay = to.toEpochDays()
    return LocalDate.fromEpochDays(Random.nextLong(fromDay, toDay))
}

fun randomUUID(): UUID = UUID.randomUUID()