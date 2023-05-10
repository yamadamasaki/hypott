package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
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
      val bg: BigDecimal,
    )

    // Note: the order of evaluation (alphabetically sorted) is crucial
    val expectation = PrimitivesFixture(
      b = random.nextBytes(1)[0],
      bg = random.nextDouble().toBigDecimal(),
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
  fun testForAnyStringWithDefaultVariant() {
    data class StringFixture(
      val s: String,
    )

    val actual = hypott.forAny(StringFixture::class)

    assertTrue(actual.s.length in LengthRange.SS.range)
    assertTrue(actual.s.all { it in alphaNumericChars })
  }

  @Test
  fun testForAnyStringWithCustomVariant() {
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

  @Test
  fun testForAnyLocalDateTime() {
    data class LocalDateTimeFixture(
      val ldt: LocalDateTime
    )

    val from = LocalDateTime.parse("1958-08-26T18:30:22")
    val until = LocalDateTime.parse("2023-05-08T18:30:22")
    val variant = LocalDateTimeVariant(from = from, until = until)

    val actual = hypott.forAny(LocalDateTimeFixture::class, variant = mapOf("ldt" to variant))

    assertTrue(from.isBefore(actual.ldt) && actual.ldt.isBefore(until))
  }

  @Test
  fun testForAnyLocalDate() {
    data class LocalDateFixture(
      val ldt: LocalDate
    )

    val from = LocalDate.parse("1958-08-26")
    val until = LocalDate.parse("2023-05-08")
    val variant = LocalDateVariant(from = from, until = until)

    val actual = hypott.forAny(LocalDateFixture::class, variant = mapOf("ldt" to variant))

    assertTrue(from.isBefore(actual.ldt) && actual.ldt.isBefore(until))
  }

  enum class TestEnum { FOO, BAR, BAZ } // enum should be on top level

  @Test
  fun testForAnyEnum() {
    data class EnumFixture(
      val e: TestEnum
    )

    hypott.forAny(EnumFixture::class)
  }

  @Test
  fun testForAnyClass() {
    data class TestClass(val s: String)
    data class ClassFixture(val c: TestClass)

    val range = IntRange(1, 4)
    val chars = "1234"
    val variant = ClassVariant(mapOf("s" to StringVariant(lengthRange = range, chars = chars)))

    val actual = hypott.forAny(ClassFixture::class, mapOf("c" to variant))

    val s = actual.c.s
    assertTrue(s.length in range && s.all { it in chars })
  }
}
