package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
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
}
