package jp.co.metabolics.hypott

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.asKotlinRandom
import java.util.Random as JRandom
import kotlin.random.Random as KRandom

class FakerTest {
  private val seed = (Math.random() * Long.MAX_VALUE).toLong()
  private lateinit var hypott: Hypott
  private lateinit var krandom: KRandom
  private lateinit var jrandom: JRandom
  private lateinit var faker: HypottFaker

  @BeforeEach
  fun beforeEach() {
    jrandom = JRandom(seed)
    krandom = jrandom.asKotlinRandom()
    faker = HypottFaker(random = jrandom)
    hypott = Hypott(seed = seed, faker = faker)
  }

  @Test
  fun testEmailFaker() {
    data class StringFixture(
      val s: String,
    )

    val variant = StringVariant(faker = "#{bothify 'fpp##iii'}")

    val actual = hypott.forAny(StringFixture::class, variant = mapOf("s" to variant))

    println("actual: $actual")
  }
}
