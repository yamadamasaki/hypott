package jp.co.metabolics.hypott

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class Hypott(
  val seed: Long = (Math.random() * 10E19).toLong(),
  val random: Random = Random(seed)
) {
  inline fun <reified T : Any> forAny(klass: KClass<T>): T {
    val memberProperties = klass.memberProperties
    val map = memberProperties.map { Pair(it.name, generateValue(it.returnType, random)) }.associate { it }
    val mapper = jacksonObjectMapper()
    val json = mapper.writeValueAsString(map)
    return mapper.readValue(json, T::class.java)
  }
}
