package jp.co.metabolics.hypott

import kotlin.random.Random
import kotlin.reflect.KType

private const val seed = 0L
private val random = Random(seed)

fun generateValue(type: KType): Any? {
  return when (type.toString()) {
    "kotlin.Int"-> generateInt(0L)
    else -> null
  }
}

fun generateInt(seed: Long): Int = random.nextInt()
