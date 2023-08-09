package jp.co.metabolics.hypott

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType

fun generateValue(type: KType, random: Random, variant: Variant?, where: Any? = null, faker: HypottFaker): Any? {
  val nullable = type.isMarkedNullable
  return when (type.toString()) {
    "kotlin.Byte", "kotlin.Byte?" -> where ?: byteGenerator(random, variant ?: ByteVariant(), nullable)
    "kotlin.Short", "kotlin.Short?" -> where ?: shortGenerator(random, variant ?: ShortVariant(), nullable)
    "kotlin.Int", "kotlin.Int?" -> where ?: intGenerator(random, variant ?: IntVariant(), nullable)
    "kotlin.Long", "kotlin.Long?" -> where ?: longGenerator(random, variant ?: LongVariant(), nullable)
    /* unsigned numbers are not yet allowed in Jackson */
    // "kotlin.UByte" -> random.nextUBytes(1)[0]
    // "kotlin.UShort" -> random.nextUInt(UShort.MIN_VALUE.toUInt(), UShort.MAX_VALUE.toUInt()).toUShort()
    // "kotlin.UInt" -> random.nextUInt()
    // "kotlin.ULong" -> random.nextULong()
    "kotlin.Float", "kotlin.Float?" -> where ?: floatGenerator(random, variant ?: FloatVariant(), nullable)
    "kotlin.Double", "kotlin.Double?" -> where ?: doubleGenerator(random, variant ?: DoubleVariant(), nullable)
    "kotlin.Boolean", "kotlin.Boolean?" -> where ?: booleanGenerator(random, variant ?: BooleanVariant(), nullable)
    // this cause java.lang.IllegalArgumentException when a char is invalid as Character (文字)
    // "kotlin.Char" -> random.nextInt(Char.MIN_VALUE.digitToInt(), Char.MAX_VALUE.digitToInt())
    "kotlin.String", "kotlin.String?" -> where
      ?: stringGenerator(random, variant ?: StringVariant(), nullable, faker)
    // "kotlin.Array" -> // ToDo
    // "kotlin.ByteArray" -> // ToDo
    // "kotlin.ShortArray" -> // ToDo
    // "kotlin.IntArray" -> // ToDo
    // "kotlin.LongArray" -> // ToDo
    // "kotlin.UByteArray" -> // ToDo
    // "kotlin.UShortArray" -> // ToDo
    // "kotlin.UIntArray" -> // ToDo
    // "kotlin.ULongArray" -> // ToDo
    "java.time.OffsetDateTime", "java.time.OffsetDateTime?" ->
      where ?: offsetDateTimeGenerator(random, variant ?: OffsetDateTimeVariant(), nullable)

    "java.time.LocalDateTime", "java.time.LocalDateTime?" ->
      where ?: localDateTimeGenerator(random, variant ?: LocalDateTimeVariant(), nullable)

    "java.time.LocalDate", "java.time.LocalDate?" ->
      where ?: localDateGenerator(random, variant ?: LocalDateVariant(), nullable)

    "java.math.BigDecimal", "java.math.BigDecimal?" ->
      where ?: bigDecimalGenerator(random, variant ?: BigDecimalVariant(), nullable)

    else -> generateClassValue(type, random, variant, where, nullable, faker)
  }
}

fun byteGenerator(random: Random, variant: Variant, nullable: Boolean): Byte? {
  val variant = variant as ByteVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else generateSequence { random.nextBytes(1)[0] }.find { variant.min <= it && it < variant.max }
}

fun shortGenerator(random: Random, variant: Variant, nullable: Boolean): Short? {
  val variant = variant as ShortVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else random.nextInt(variant.min.toInt(), variant.max.toInt()).toShort()
}

fun intGenerator(random: Random, variant: Variant, nullable: Boolean): Int? {
  val variant = variant as IntVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else random.nextInt(variant.min, variant.max)
}

fun longGenerator(random: Random, variant: Variant, nullable: Boolean): Long? {
  val variant = variant as LongVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else random.nextLong(variant.min, variant.max)
}

fun floatGenerator(random: Random, variant: Variant, nullable: Boolean): Float? {
  val variant = variant as FloatVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else random.nextDouble(variant.min.toDouble(), variant.max.toDouble()).toFloat()
}

fun doubleGenerator(random: Random, variant: Variant, nullable: Boolean): Double? {
  val variant = variant as DoubleVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else random.nextDouble(variant.min, variant.max)
}

fun booleanGenerator(random: Random, variant: Variant, nullable: Boolean): Boolean? {
  val variant = variant as BooleanVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else random.nextBoolean()
}

fun bigDecimalGenerator(random: Random, variant: Variant, nullable: Boolean): BigDecimal? {
  val variant = variant as BigDecimalVariant // ToDo Exception
  return if (nullable && random.nextFloat() < variant.nullRatio) null
  else random.nextDouble(variant.min.toDouble(), variant.max.toDouble()).toBigDecimal()
}

fun stringGenerator(random: Random, variant: Variant, nullable: Boolean, faker: HypottFaker): String? {
  assert(variant is StringVariant)
  val variant = variant as StringVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  if (variant.faker != null) return faker.expression(variant.faker)
  val (lengthRange, chars) = variant
  assert(0 < lengthRange.first)
  assert(chars.isNotEmpty())
  val length = random.nextInt(lengthRange)
  return (1..length).map { chars.random(random) }.joinToString("")
}

fun offsetDateTimeGenerator(random: Random, variant: Variant, nullable: Boolean): OffsetDateTime? {
  val variant = variant as OffsetDateTimeVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val (from, until, zoneId) = variant
  return OffsetDateTime.ofInstant(
    Instant.ofEpochSecond(random.nextLong(from.toEpochSecond(), until.toEpochSecond())),
    zoneId // zoneId also should be randomized?
  )
}

fun localDateTimeGenerator(random: Random, variant: Variant, nullable: Boolean): LocalDateTime? {
  val variant = variant as LocalDateTimeVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val (from, until, offset) = variant
  return LocalDateTime.ofEpochSecond(
    random.nextLong(from.toEpochSecond(offset), until.toEpochSecond(offset)), 0, offset
  )
}

fun localDateGenerator(random: Random, variant: Variant, nullable: Boolean): LocalDate? {
  val variant = variant as LocalDateVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val (from, until) = variant
  return LocalDate.ofEpochDay(random.nextLong(from.toEpochDay(), until.toEpochDay()))
}

fun generateClassValue(
  type: KType,
  random: Random,
  variant: Variant?,
  where: Any?,
  nullable: Boolean,
  faker: HypottFaker
): Any? { // ToDo Null return
  val classifier = type.classifier ?: return null
  val klass = classifier as KClass<Any>
  return when {
    klass.java.isEnum -> where ?: generalEnumGenerator(klass, random, variant ?: EnumVariant(), nullable)
    klass.qualifiedName?.matches(Regex("""kotlin\.collections\.List.*""")) ?: false ->
      where ?: generalListGenerator(klass, type.toString(), random, variant ?: ListVariant(), nullable, faker)

    klass.qualifiedName?.matches(Regex("""kotlin\.collections\.Set.*""")) ?: false ->
      where ?: generalSetGenerator(klass, type.toString(), random, variant ?: SetVariant(), nullable, faker)

    klass.qualifiedName?.matches(Regex("""kotlin\.collections\.Map.*""")) ?: false ->
      where ?: generalMapGenerator(klass, type.toString(), random, variant ?: MapVariant(), nullable, faker)

    else -> generalClassGenerator(klass, random, variant ?: ClassVariant(), where, nullable, faker)
  }
}

inline fun <reified T : Any> generalClassGenerator(
  klass: KClass<T>, random: Random, variant: Variant, where: Any?, nullable: Boolean, faker: HypottFaker
): T? {
  val variant = variant as ClassVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val hypott = Hypott(random = random, faker = faker)
  return hypott.forAny(klass, variant.members, where)
}

fun generalEnumGenerator(klass: KClass<*>, random: Random, variant: Variant, nullable: Boolean): Enum<*>? {
  val variant = variant as EnumVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val constants = klass.java.enumConstants
  return constants.random(random) as Enum<*> // ToDo Exception
}

fun <T : Any> generalListGenerator(
  klass: KClass<T>, typeName: String, random: Random, variant: Variant, nullable: Boolean, faker: HypottFaker
): T? {
  val variant = variant as ListVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val typeParameterName = Regex(".*<(.*)>\\??").matchEntire(typeName)?.groupValues?.get(1) // ToDo Exception
  val type = Class.forName(kotlin2javaClassNameMap[typeParameterName]).kotlin.createType() // ToDo Exception
  val length = variant.lengthRange.random(random)
  return (1..length).map { generateValue(type, random, variant.elementsVariant, faker = faker) } as T // ToDo Exception
}

fun <T : Any> generalSetGenerator(
  klass: KClass<T>, typeName: String, random: Random, variant: Variant, nullable: Boolean, faker: HypottFaker
): T? {
  val variant = variant as SetVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val typeParameterName = Regex(".*<(.*)>\\??").matchEntire(typeName)?.groupValues?.get(1) // ToDo Exception
  val type = Class.forName(kotlin2javaClassNameMap[typeParameterName]).kotlin.createType() // ToDo Exception
  val length = variant.lengthRange.random(random)
  return (1..length).map { generateValue(type, random, variant.elementsVariant, faker = faker) }
    .toSet() as T // ToDo Exception
}

fun <T : Any> generalMapGenerator(
  klass: KClass<T>, typeName: String, random: Random, variant: Variant, nullable: Boolean, faker: HypottFaker
): T? {
  val variant = variant as MapVariant // ToDo Exception
  if (nullable && random.nextFloat() < variant.nullRatio) return null
  val typeParameterNames = Regex(""".*<(.*),\s*(.*)>\??""").matchEntire(typeName)?.groupValues
    ?: listOf("<kotlin.String, kotlin.String>", "kotlin.String", "kotlin.String") // ToDo Error
  val (_, keyTypeName, valueTypeName) = typeParameterNames // ToDo Exception
  val keyType = Class.forName(kotlin2javaClassNameMap[keyTypeName]).kotlin.createType() // ToDo Exception
  val valueType = Class.forName(kotlin2javaClassNameMap[valueTypeName]).kotlin.createType() // ToDo Exception
  val length = variant.lengthRange.random(random)
  return (1..length).map {
    Pair(
      generateValue(keyType, random, variant.keyVariant, faker = faker),
      generateValue(valueType, random, variant.valueVariant, faker = faker),
    )
  }.associate { it } as T // ToDo Exception
}
