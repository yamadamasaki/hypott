package jp.co.metabolics.hypott

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType

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
    else -> generateClassValue(type, random, variant)
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

fun generateClassValue(type: KType, random: Random, variant: Variant?): Any? { // ToDo Null return
  val classifier = type.classifier ?: return null
  val klass = classifier as KClass<Any>
  return when {
    klass.java.isEnum -> generalEnumGenerator(klass, random)
    klass.qualifiedName?.matches(Regex("""kotlin\.collections\.List.*""")) ?: false ->
      generalListGenerator(klass, type.toString(), random, variant ?: ListVariant())

    klass.qualifiedName?.matches(Regex("""kotlin\.collections\.Set.*""")) ?: false ->
      generalSetGenerator(klass, type.toString(), random, variant ?: SetVariant())

    klass.qualifiedName?.matches(Regex("""kotlin\.collections\.Map.*""")) ?: false ->
      generalMapGenerator(klass, type.toString(), random, variant ?: MapVariant())

    else -> generalClassGenerator(klass, random, variant ?: ClassVariant())
  }
}

inline fun <reified T : Any> generalClassGenerator(klass: KClass<T>, random: Random, variant: Variant): T {
  val variant = variant as ClassVariant // ToDo Exception
  val hypott = Hypott(random = random)
  return hypott.forAny(klass, variant.members)
}

fun generalEnumGenerator(klass: KClass<*>, random: Random): Enum<*> {
  val constants = klass.java.enumConstants
  return constants.random(random) as Enum<*> // ToDo Exception
}

fun <T : Any> generalListGenerator(klass: KClass<T>, typeName: String, random: Random, variant: Variant): List<*> {
  val variant = variant as ListVariant // ToDo Exception
  val typeParameterName = Regex(".*<(.*)>").matchEntire(typeName)?.groupValues?.get(1) // ToDo Exception
  val type = Class.forName(kotlin2javaClassNameMap[typeParameterName]).kotlin.createType() // ToDo Exception
  val length = variant.lengthRange.random(random)
  return (1..length).map { generateValue(type, random, variant.elementsVariant) }
}

fun <T : Any> generalSetGenerator(klass: KClass<T>, typeName: String, random: Random, variant: Variant): Set<*> {
  val variant = variant as SetVariant // ToDo Exception
  val typeParameterName = Regex(".*<(.*)>").matchEntire(typeName)?.groupValues?.get(1) // ToDo Exception
  val type = Class.forName(kotlin2javaClassNameMap[typeParameterName]).kotlin.createType() // ToDo Exception
  val length = variant.lengthRange.random(random)
  return (1..length).map { generateValue(type, random, variant.elementsVariant) }.toSet()
}

fun <T : Any> generalMapGenerator(klass: KClass<T>, typeName: String, random: Random, variant: Variant): Map<*, *> {
  val variant = variant as MapVariant // ToDo Exception
  val typeParameterNames = Regex(""".*<(.*),\s*(.*)>""").matchEntire(typeName)?.groupValues
    ?: listOf("<kotlin.String, kotlin.String>", "kotlin.String", "kotlin.String") // ToDo Error
  val (_, keyTypeName, valueTypeName) = typeParameterNames // ToDo Exception
  val keyType = Class.forName(kotlin2javaClassNameMap[keyTypeName]).kotlin.createType() // ToDo Exception
  val valueType = Class.forName(kotlin2javaClassNameMap[valueTypeName]).kotlin.createType() // ToDo Exception
  val length = variant.lengthRange.random(random)
  return (1..length).map {
    Pair(
      generateValue(keyType, random, variant.keyVariant),
      generateValue(valueType, random, variant.valueVariant),
    )
  }.associate { it }
}
