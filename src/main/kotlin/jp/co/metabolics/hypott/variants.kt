package jp.co.metabolics.hypott

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset

open class Variant

data class StringVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val chars: String = alphaNumericChars,
  val regex: Regex? = null // ToDo Feature
) : Variant()

enum class LengthRange(val range: IntRange) {
  SS(IntRange(1 shl 2, 1 shl 4)), // ~16
  S(IntRange(1 shl 4, 1 shl 6)), // ~64
  M(IntRange(1 shl 6, 1 shl 8)), // ~256
  L(IntRange(1 shl 8, 1 shl 10)), // ~1024
  LL(IntRange(1 shl 10, 1 shl 12)), // ~4096
}

val numericChars = ('0'..'9').joinToString("")
val alphabeticalChars = (('A'..'Z') + ('a'..'z')).joinToString("")
val alphaNumericChars = alphabeticalChars + numericChars
val kanaChars = ('あ'..'ン').joinToString("")
val kanjiChars = ('一'..'柋').joinToString("")
val kanaKanjiChars = kanaChars + kanjiChars

data class OffsetDateTimeVariant(
  val from: OffsetDateTime = OffsetDateTime.MIN,
  val until: OffsetDateTime = OffsetDateTime.MAX,
  val zoneId: ZoneId = ZoneId.systemDefault(),
) : Variant()

data class LocalDateTimeVariant(
  val from: LocalDateTime = LocalDateTime.MIN,
  val until: LocalDateTime = LocalDateTime.MAX,
  val offset: ZoneOffset = ZoneOffset.UTC,
) : Variant()

data class LocalDateVariant(
  val from: LocalDate = LocalDate.MIN,
  val until: LocalDate = LocalDate.MAX,
) : Variant()

data class ClassVariant(
  val members: Map<String, Variant> = mapOf()
) : Variant()

data class NumberVariant(
  val valueRange: IntRange = IntRange(Int.MIN_VALUE, Int.MAX_VALUE),
  val distribution: String? = null, // ToDo Feature
) : Variant()

data class ListVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val elementsVariant: Variant? = null,
) : Variant()

data class SetVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val elementsVariant: Variant? = null,
) : Variant()

data class MapVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val keyVariant: Variant? = null,
  val valueVariant: Variant? = null,
) : Variant()
