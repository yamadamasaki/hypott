package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class ForAnyKtTest {
  private val seed = (Math.random() * 10E19).toLong()
  private lateinit var hypott: Hypott

  @BeforeEach
  fun beforeEach() {
    hypott = Hypott(seed = seed)
  }

  @Test
  fun testForAny() {
    data class TestInt(val i: Int)

    val instance = hypott.forAny(TestInt::class)
    println("instance: $instance")
    assertEquals(Random(seed).nextInt(), instance.i)
  }
}
