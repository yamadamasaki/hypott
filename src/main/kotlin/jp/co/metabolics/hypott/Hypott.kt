package jp.co.metabolics.hypott

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.rng.UniformRandomProvider
import org.apache.commons.rng.simple.RandomSource
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class Hypott(
  val seed: Long = (Math.random() * 10E19).toLong(),
  val random: UniformRandomProvider = RandomSource.JDK.create(seed),
  val faker: HypottFaker = HypottFaker(),
  val showValues: Boolean = true,
) {
  inline fun <reified T : Any> forAny(
    klass: KClass<T>,
    variant: Map<String, Variant> = emptyMap(),
    where: Any? = null,
    showValues: Boolean = this.showValues,
  ): T {
    val memberProperties = klass.memberProperties
    val whereProperties = if (where == null) null else where::class.members
    val map = memberProperties.map {
      val typeName = it.name
      Pair(
        typeName,
        generateValue(
          it.returnType, random, variant[typeName],
          whereProperties?.find { prop -> prop.name == typeName }?.call(where),
          faker
        )
      )
    }.associate { it }
    if (showValues) println("hypott: ${klass.simpleName} = $map")
    val mapper = jacksonObjectMapper()
    mapper.registerModule(JavaTimeModule())
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
    val json = mapper.writeValueAsString(map)
    return mapper.readValue(json, T::class.java)
  }
}
