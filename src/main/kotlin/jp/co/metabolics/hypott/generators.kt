package jp.co.metabolics.hypott

import kotlin.reflect.KType

fun generateValue(type: KType): Any? {
  return when (type.toString()) {
    "kotlin.Int"-> 1
    else -> null
  }
}
