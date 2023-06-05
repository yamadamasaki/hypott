package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import kotlin.random.Random

class NullableKtTest {
  private val seed = (Math.random() * Long.MAX_VALUE).toLong()
  private lateinit var hypott: Hypott
  private lateinit var random: Random

  @BeforeEach
  fun beforeEach() {
    hypott = Hypott(seed = seed)
    random = Random(seed)
  }

  @Test
  fun testNullabilityByte() {
    data class ByteFixture(val b: Byte?)

    val variant = ByteVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(ByteFixture::class, variant = mapOf("b" to variant)) }
      .filter { it.b == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityShort() {
    data class ShortFixture(val s: Short?)

    val variant = ShortVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(ShortFixture::class, variant = mapOf("s" to variant)) }
      .filter { it.s == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityInt() {
    data class IntFixture(val i: Int?)

    val variant = IntVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(IntFixture::class, variant = mapOf("i" to variant)) }
      .filter { it.i == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityLong() {
    data class LongFixture(val l: Long?)

    val variant = LongVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(LongFixture::class, variant = mapOf("l" to variant)) }
      .filter { it.l == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityFloat() {
    data class FloatFixture(val f: Float?)

    val variant = FloatVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(FloatFixture::class, variant = mapOf("f" to variant)) }
      .filter { it.f == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityDouble() {
    data class DoubleFixture(val d: Double?)

    val variant = DoubleVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(DoubleFixture::class, variant = mapOf("d" to variant)) }
      .filter { it.d == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityBoolean() {
    data class BooleanFixture(val b: Boolean?)

    val variant = BooleanVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(BooleanFixture::class, variant = mapOf("b" to variant)) }
      .filter { it.b == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityBigDecimal() {
    data class BigDecimalFixture(val b: BigDecimal?)

    val variant = BigDecimalVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(BigDecimalFixture::class, variant = mapOf("b" to variant)) }
      .filter { it.b == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityString() {
    data class StringFixture(val s: String?)

    val variant = StringVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(StringFixture::class, variant = mapOf("s" to variant)) }
      .filter { it.s == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityOffsetDateTime() {
    data class OffsetDateTimeFixture(val odt: OffsetDateTime?)

    val variant = OffsetDateTimeVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(OffsetDateTimeFixture::class, variant = mapOf("odt" to variant)) }
      .filter { it.odt == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityLocalDateTime() {
    data class LocalDateTimeFixture(val ldt: LocalDateTime?)

    val variant = LocalDateTimeVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(LocalDateTimeFixture::class, variant = mapOf("ldt" to variant)) }
      .filter { it.ldt == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityLocalDate() {
    data class LocalDateFixture(val ld: LocalDate?)

    val variant = LocalDateVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(LocalDateFixture::class, variant = mapOf("ld" to variant)) }
      .filter { it.ld == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  enum class TestEnum { FOO, BAR, BAZ } // enum should be on top level

  @Test
  fun testNullabilityEnum() {
    data class EnumFixture(val e: TestEnum?)

    val variant = EnumVariant(nullRatio = 0.9F)

    val nNull = (0..99)
      .map { hypott.forAny(EnumFixture::class, variant = mapOf("e" to variant)) }
      .filter { it.e == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityList_String() {
    data class ListFixture(val l: List<String>?)

    val variant = ListVariant(nullRatio = .9F)

    val nNull = (0..99)
      .map { hypott.forAny(ListFixture::class, mapOf("l" to variant)) }
      .filter { it.l == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityList_Int() {
    data class ListFixture(val l: List<Int>?)

    val variant = ListVariant(nullRatio = .9F)

    val nNull = (0..99)
      .map { hypott.forAny(ListFixture::class, mapOf("l" to variant)) }
      .filter { it.l == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilitySet_String() {
    data class SetFixture(val s: Set<String>?)

    val variant = SetVariant(nullRatio = .9F)

    val nNull = (0..99)
      .map { hypott.forAny(SetFixture::class, mapOf("s" to variant)) }
      .filter { it.s == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }

  @Test
  fun testNullabilityMap_String_Int() {
    data class MapFixture(val m: Map<String, Int>?)

    val variant = MapVariant(nullRatio = .9F)

    val nNull = (0..99)
      .map { hypott.forAny(MapFixture::class, mapOf("m" to variant)) }
      .filter { it.m == null }.size
    println("nNull: $nNull")
    Assertions.assertNotEquals(nNull, 0) // not rigid but almost correct
  }
}
