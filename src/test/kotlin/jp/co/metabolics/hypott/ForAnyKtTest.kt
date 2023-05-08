package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import kotlin.random.Random

class ForAnyKtTest {
  private val seed = (Math.random() * Long.MAX_VALUE).toLong()
  private lateinit var hypott: Hypott
  private lateinit var random: Random

  @BeforeEach
  fun beforeEach() {
    hypott = Hypott(seed = seed)
    random = Random(seed)
  }

  @Test
  // this may depend on test-target implementation
  fun testForAnyPrimitives() {
    data class PrimitivesFixture(
      val b: Byte, val s: Short, val i: Int, val l: Long,
      // val ub: UByte, val us: UShort, val ui: UInt, val ul: ULong,
      val f: Float, val d: Double, val boolean: Boolean,
    )

    // Note: the order of evaluation (alphabetically sorted) is crucial
    val expectation = PrimitivesFixture(
      b = random.nextBytes(1)[0],
      boolean = random.nextBoolean(),
      d = random.nextDouble(),
      f = random.nextFloat(),
      i = random.nextInt(),
      l = random.nextLong(),
      s = random.nextInt(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort(),
      /*
      ub = random.nextUBytes(1)[0],
      ui = random.nextUInt(),
      ul = random.nextULong(),
      us = random.nextUInt(UShort.MIN_VALUE.toUInt(), UShort.MAX_VALUE.toUInt()).toUShort(),
       */
    )

    val actual = hypott.forAny(PrimitivesFixture::class)

    assertEquals(expectation, actual)
  }

  @Test
  fun testForAnyString() {
    data class StringFixture(
      val s: String,
    )

    val range = IntRange(1, 4)
    val chars = ('0'..'9').joinToString("")
    val variant = StringVariant(lengthRange = range, chars = chars)

    val actual = hypott.forAny(StringFixture::class, variant = mapOf("s" to variant))

    assertTrue(actual.s.length in range)
    assertTrue(actual.s.all { it in chars })
  }

  @Test
  fun testForAnyOffsetDateTime() {
    data class OffsetDateTimeFixture(
      val odt: OffsetDateTime
    )

    val from = OffsetDateTime.parse("1958-08-26T18:30:22+09:00")
    val until = OffsetDateTime.parse("2023-05-08T18:30:22+09:00")
    val variant = OffsetDateTimeVariant(from = from, until = until)

    val actual = hypott.forAny(OffsetDateTimeFixture::class, variant = mapOf("odt" to variant))

    assertTrue(from.isBefore(actual.odt) && actual.odt.isBefore(until))
  }
}
