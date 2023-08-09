package jp.co.metabolics.hypott

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset

open class Variant

enum class Distribution {
  UNIFORM,
  NORMAL,
  LOG_NORMAL,
}

data class ByteVariant(
  val min: Byte = Byte.MIN_VALUE,
  val max: Byte = Byte.MAX_VALUE,
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class ShortVariant(
  val min: Short = Short.MIN_VALUE,
  val max: Short = Short.MAX_VALUE,
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class IntVariant(
  val min: Int = Int.MIN_VALUE,
  val max: Int = Int.MAX_VALUE,
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class LongVariant(
  val min: Long = Long.MIN_VALUE,
  val max: Long = Long.MAX_VALUE,
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class FloatVariant(
  val min: Float = Float.MIN_VALUE,
  val max: Float = Float.MAX_VALUE,
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class DoubleVariant(
  val min: Double = Double.MIN_VALUE,
  val max: Double = Double.MAX_VALUE,
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

// this is the correct max BitInteger value, but it takes too much time to compute it
//private val maxBigInteger: BigInteger = BigInteger("2").pow(Int.MAX_VALUE-1) - BigInteger.ONE + BigInteger("2").pow(Int.MAX_VALUE-1)
//private val maxBigDecimal: BigDecimal = BigDecimal(maxBigInteger)
// so i use BigDecimal(Double.MAX_VALUE)

data class BigDecimalVariant(
  val min: BigDecimal = BigDecimal(Double.MIN_VALUE),
  val max: BigDecimal = BigDecimal(Double.MAX_VALUE),
  // val distribution: Distribution = Distribution.Uniform, // ToDo Feature
  val nullRatio: Float = .5F
) : Variant()

data class BooleanVariant(
  val nullRatio: Float = .5F
) : Variant()

data class StringVariant(
  val lengthRange: IntRange = LengthRange.SS.range,
  val chars: String = alphaNumericChars,
  val regex: Regex? = null, // ToDo Feature
  val nullRatio: Float = .5F,
  val faker: String? = null,
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
