package jp.co.metabolics.hypott

import kotlin.random.Random
import kotlin.reflect.KType

fun generateValue(type: KType, random: Random): Any? {
  return when (type.toString()) {
    "kotlin.Int" -> generateInt(random)
    else -> null
  }
}

fun generateInt(random: Random): Int = random.nextInt()
