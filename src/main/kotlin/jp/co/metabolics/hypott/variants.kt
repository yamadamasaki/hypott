package jp.co.metabolics.hypott

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset

open class Variant

enum class Distribution {
  Uniform,
}

enum class NegPos {
  Negative,
  Positive,
  NotNegative,
  NotPositive,
  Zero,
  All,
}

data class ByteRange(val min: Byte, val max: Byte)

data class ByteVariant(
  // val range: ByteRange = ByteRange(Byte.MIN_VALUE, Byte.MAX_VALUE), // ToDo Feature
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  // val negpos: NegPos = NegPos.All, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class ShortRange(val min: Short, val max: Short)

data class ShortVariant(
  // val range: ShortRange = ShortRange(Short.MIN_VALUE, Short.MAX_VALUE), // ToDo Feature
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  // val negpos: NegPos = NegPos.All, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class IntVariant(
  // val range: IntRange = IntRange(Int.MIN_VALUE, Int.MAX_VALUE), // ToDo Feature
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  // val negpos: NegPos = NegPos.All, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class LongVariant(
  // val range: LongRange = LongRange(Long.MIN_VALUE, Long.MAX_VALUE), // ToDo Feature
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  // val negpos: NegPos = NegPos.All, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class FloatRange(val min: Float, val max: Float)

data class FloatVariant(
  // val range: FloatRange = FloatRange(Float.MIN_VALUE, Float.MAX_VALUE), // ToDo Feature
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  // val negpos: NegPos = NegPos.All, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class DoubleRange(val min: Double, val max: Double)

data class DoubleVariant(
  val range: DoubleRange = DoubleRange(Double.MIN_VALUE, Double.MAX_VALUE), // ToDo Feature
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  // val negpos: NegPos = NegPos.All, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class BigDecimalVariant(
  // val range: LongRange = LongRange(Long.MIN_VALUE, Long.MAX_VALUE), // ToDo Feature
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  // val negpos: NegPos = NegPos.All, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class BooleanVariant(
  val nullRatio: Float = .5F
) : Variant()

data class StringVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val chars: String = alphaNumericChars,
  val regex: Regex? = null, // ToDo Feature
  val nullRatio: Float = .5F
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
  val nullRatio: Float = .5F,
) : Variant()

data class LocalDateTimeVariant(
  val from: LocalDateTime = LocalDateTime.MIN,
  val until: LocalDateTime = LocalDateTime.MAX,
  val offset: ZoneOffset = ZoneOffset.UTC,
  val nullRatio: Float = .5F,
) : Variant()

data class LocalDateVariant(
  val from: LocalDate = LocalDate.MIN,
  val until: LocalDate = LocalDate.MAX,
  val nullRatio: Float = .5F,
) : Variant()

data class ClassVariant(
  val members: Map<String, Variant> = mapOf(),
  val nullRatio: Float = .5F,
) : Variant()

data class EnumVariant(
  val nullRatio: Float = .5F,
) : Variant()

data class ListVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val elementsVariant: Variant? = null,
  val nullRatio: Float = .5F,
) : Variant()

data class SetVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val elementsVariant: Variant? = null,
  val nullRatio: Float = .5F,
) : Variant()

data class MapVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val keyVariant: Variant? = null,
  val valueVariant: Variant? = null,
  val nullRatio: Float = .5F,
) : Variant()
