package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.random.Random

class ValueRangeTest {
  private val seed = (Math.random() * Long.MAX_VALUE).toLong()
  private lateinit var hypott: Hypott
  private lateinit var random: Random

  @BeforeEach
  fun beforeEach() {
    hypott = Hypott(seed = seed)
    random = Random(seed)
  }

  @Test
  fun testRangePrimitives() {
    data class PrimitivesFixture(
      val b: Byte, val s: Short, val i: Int, val l: Long,
      // val ub: UByte, val us: UShort, val ui: UInt, val ul: ULong,
      val f: Float, val d: Double, val bd: BigDecimal,
    )

    val range = mapOf(
      "b" to ByteVariant(min = 3, max = 5),
      "s" to ShortVariant(min = 3, max = 5),
      "i" to IntVariant(min = 3, max = 5),
      "l" to LongVariant(min = 3, max = 5),
      "f" to FloatVariant(min = 3.0F, max = 5.0F),
      "d" to DoubleVariant(min = 3.0, max = 5.0),
      "bd" to BigDecimalVariant(min = BigDecimal("3"), max = BigDecimal("5")),
    )

    val actual = hypott.forAny(PrimitivesFixture::class, variant = range)

    Assertions.assertTrue(3 <= actual.b && actual.d < 5)
    Assertions.assertTrue(3 <= actual.s && actual.d < 5)
    Assertions.assertTrue(3 <= actual.i && actual.d < 5)
    Assertions.assertTrue(3 <= actual.l && actual.d < 5)
    Assertions.assertTrue(3.0 <= actual.f && actual.f < 5.0)
    Assertions.assertTrue(3.0 <= actual.d && actual.d < 5.0)
    Assertions.assertTrue(BigDecimal("3") <= actual.bd && actual.bd < BigDecimal("5"))
  }
}
