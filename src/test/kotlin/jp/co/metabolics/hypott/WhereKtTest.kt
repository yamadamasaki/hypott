package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import kotlin.random.Random


class WhereKtTest {
  private val seed = (Math.random() * Long.MAX_VALUE).toLong()
  private lateinit var hypott: Hypott
  private lateinit var random: Random

  @BeforeEach
  fun beforeEach() {
    hypott = Hypott(seed = seed)
    random = Random(seed)
  }

  @Test
  fun testWherePrimitives() {
    data class PrimitivesFixture(
      val b: Byte, val s: Short, val i: Int, val l: Long,
      // val ub: UByte, val us: UShort, val ui: UInt, val ul: ULong,
      val f: Float, val d: Double, val boolean: Boolean,
      val bg: BigDecimal,
    )

    val where = object {
      val b: Byte = 26
      val bg: BigDecimal = BigDecimal(1208)
      val boolean: Boolean = false
      val d: Double = 2342.343
      val f: Float = (-209847.34534958).toFloat()
      val i: Int = 98171
      val l: Long = 98171L
      val s: Short = -298
    }

    // Note: the order of evaluation (alphabetically sorted) is crucial
    val expectation = PrimitivesFixture(
      b = 26,
      bg = BigDecimal(1208),
      boolean = false,
      d = 2342.343,
      f = (-209847.34534958).toFloat(),
      i = 98171,
      l = 98171L,
      s = -298,
      /*
      ub = random.nextUBytes(1)[0],
      ui = random.nextUInt(),
      ul = random.nextULong(),
      us = random.nextUInt(UShort.MIN_VALUE.toUInt(), UShort.MAX_VALUE.toUInt()).toUShort(),
       */
    )

    val actual = hypott.forAny(PrimitivesFixture::class, where = where)

    Assertions.assertEquals(expectation, actual)
  }

  @Test
  fun testWhereString() {
    data class StringFixture(
      val s: String,
    )

    val where = object {
      val s: String = "where"
    }

    val actual = hypott.forAny(StringFixture::class, where = where)

    Assertions.assertEquals(actual.s, where.s)
  }

  @Test
  fun testWhereOffsetDateTime() {
    data class OffsetDateTimeFixture(
      val odt: OffsetDateTime
    )

    val where = object {
      val odt: OffsetDateTime = OffsetDateTime.parse("1958-08-26T18:30:22+09:00")
    }

    val actual = hypott.forAny(OffsetDateTimeFixture::class, where = where)

    // なぜか actual には Z 時間が入っていて, equals は true にならない. where では代入しているだけのはずだが
    Assertions.assertEquals(actual.odt.toInstant(), where.odt.toInstant())
  }

  @Test
  fun testWhereLocalDateTime() {
    data class LocalDateTimeFixture(
      val ldt: LocalDateTime
    )

    val where = object {
      val ldt: LocalDateTime = LocalDateTime.parse("1958-08-26T18:30:22")
    }

    val actual = hypott.forAny(LocalDateTimeFixture::class, where = where)

    Assertions.assertEquals(actual.ldt, where.ldt)
  }

  @Test
  fun testWhereLocalDate() {
    data class LocalDateFixture(
      val ld: LocalDate
    )

    val where = object {
      val ld: LocalDate = LocalDate.parse("1958-08-26")
    }

    val actual = hypott.forAny(LocalDateFixture::class, where = where)

    Assertions.assertEquals(actual.ld, where.ld)
  }
}
