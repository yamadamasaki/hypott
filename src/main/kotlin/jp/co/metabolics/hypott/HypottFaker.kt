package jp.co.metabolics.hypott

import com.github.javafaker.Faker
import java.util.*

class HypottFaker(
  locale: Locale = Locale.ENGLISH,
  random: Random = Random()
) : Faker(locale, random)
