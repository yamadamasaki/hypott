package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class ForAnyKtTest {
  private val seed = (Math.random() * 10E19).toLong()
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
}
