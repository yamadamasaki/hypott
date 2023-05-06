package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class ForAnyKtTest {
  @Test
  fun testForAny() {
    data class TestInt(val i: Int)

    val instance = forAny(TestInt::class)
    println("instance: $instance")
    assertEquals(Random(0L).nextInt(), instance.i)
  }
}
