package jp.co.metabolics.hypott

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.reflect.KClass
import kotlin.reflect.KType

fun generateValue(type: KType, random: Random, variant: Variant?): Any? {
  return when (type.toString()) {
    "kotlin.Byte" -> random.nextBytes(1)[0]
    "kotlin.Short" -> random.nextInt(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
    "kotlin.Int" -> random.nextInt()
    "kotlin.Long" -> random.nextLong()
    /* unsigned numbers are not yet allowed in Jackson */
    // "kotlin.UByte" -> random.nextUBytes(1)[0]
    // "kotlin.UShort" -> random.nextUInt(UShort.MIN_VALUE.toUInt(), UShort.MAX_VALUE.toUInt()).toUShort()
    // "kotlin.UInt" -> random.nextUInt()
    // "kotlin.ULong" -> random.nextULong()
    "kotlin.Float" -> random.nextFloat()
    "kotlin.Double" -> random.nextDouble()
    "kotlin.Boolean" -> random.nextBoolean()
    // this cause java.lang.IllegalArgumentException when a char is invalid as Character (文字)
    // "kotlin.Char" -> random.nextInt(Char.MIN_VALUE.digitToInt(), Char.MAX_VALUE.digitToInt())
    "kotlin.String" -> stringGenerator(random, variant ?: StringVariant())
    // "kotlin.Array" -> // ToDo
    // "kotlin.ByteArray" -> // ToDo
    // "kotlin.ShortArray" -> // ToDo
    // "kotlin.IntArray" -> // ToDo
    // "kotlin.LongArray" -> // ToDo
    // "kotlin.UByteArray" -> // ToDo
    // "kotlin.UShortArray" -> // ToDo
    // "kotlin.UIntArray" -> // ToDo
    // "kotlin.ULongArray" -> // ToDo
    "java.time.OffsetDateTime" -> offsetDateTimeGenerator(random, variant ?: OffsetDateTimeVariant())
    "java.time.LocalDateTime" -> localDateTimeGenerator(random, variant ?: LocalDateTimeVariant())
    "java.time.LocalDate" -> localDateGenerator(random, variant ?: LocalDateVariant())
    "java.math.BigDecimal" -> random.nextDouble().toBigDecimal()
    else -> generateClassValue(type, random)
  }
}

fun stringGenerator(random: Random, variant: Variant): String {
  assert(variant is StringVariant)
  val variant = variant as StringVariant // ToDo Exception
  val (lengthRange, chars) = variant
  assert(0 < lengthRange.min())
  assert(chars.isNotEmpty())
  val length = random.nextInt(lengthRange)
  return (1..length).map { chars.random(random) }.joinToString("")
}

fun offsetDateTimeGenerator(random: Random, variant: Variant): OffsetDateTime {
  val variant = variant as OffsetDateTimeVariant // ToDo Exception
  val (from, until, zoneId) = variant
  return OffsetDateTime.ofInstant(
    Instant.ofEpochSecond(random.nextLong(from.toEpochSecond(), until.toEpochSecond())),
    zoneId // zoneId also should be randomized?
  )
}

fun localDateTimeGenerator(random: Random, variant: Variant): LocalDateTime {
  val variant = variant as LocalDateTimeVariant // ToDo Exception
  val (from, until, offset) = variant
  return LocalDateTime.ofEpochSecond(
    random.nextLong(from.toEpochSecond(offset), until.toEpochSecond(offset)), 0, offset
  )
}

fun localDateGenerator(random: Random, variant: Variant): LocalDate {
  val variant = variant as LocalDateVariant // ToDo Exception
  val (from, until) = variant
  return LocalDate.ofEpochDay(random.nextLong(from.toEpochDay(), until.toEpochDay()))
}

fun generateClassValue(type: KType, random: Random): Any? { // ToDo Null return
  val classifier = type.classifier ?: return null
  val klass = classifier as KClass<*>
  return when {
    klass.java.isEnum -> generalEnumGenerator(klass, random)
    else -> generalClassGenerator(klass, random)
  }
}

fun generalClassGenerator(klass: KClass<*>, random: Random): Class<*>? {
  return null
}

fun generalEnumGenerator(klass: KClass<*>, random: Random): Enum<*> {
  val constants = klass.java.enumConstants
  return constants.random(random) as Enum<*> // ToDo Exception
}
