package jp.co.metabolics.hypott

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ForAnyKtTest {
  @Test
  fun testForAny() {
    data class TestInt(val i: Int)

    val instance = forAny(TestInt::class)
    assertEquals(instance.i, 1)
  }
}
