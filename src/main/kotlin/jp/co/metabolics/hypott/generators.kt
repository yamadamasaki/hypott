package jp.co.metabolics.hypott

import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.reflect.KType

fun generateValue(type: KType, random: Random, variant: Variant): Any? {
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
    "kotlin.String" -> stringGenerator(random, variant)
    // "kotlin.Array" -> // ToDo
    // "kotlin.ByteArray" -> // ToDo
    // "kotlin.ShortArray" -> // ToDo
    // "kotlin.IntArray" -> // ToDo
    // "kotlin.LongArray" -> // ToDo
    // "kotlin.UByteArray" -> // ToDo
    // "kotlin.UShortArray" -> // ToDo
    // "kotlin.UIntArray" -> // ToDo
    // "kotlin.ULongArray" -> // ToDo
    else -> null
  }
}

fun stringGenerator(random: Random, variant: Variant): String {
  val variant = variant as StringVariant
  val lengthRange = variant.lengthRange
  assert(lengthRange.min() > 0)
  val length = random.nextInt(lengthRange)
  val baseChars = variant.chars
  return (1..length).map { baseChars.random(random) }.joinToString("")
}
