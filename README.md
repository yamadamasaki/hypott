# Hypott - a Hypothesis Testing Library for Kotlin (jvm)

Hypott is a small software testing library.

Hypott is written in Kotlin and is intended for use in Kotlin (jvm) projects, but can be used with any testing framework (or, if you prefer, without a testing framework and independent of testing).

Hypott can only do two things

- Describe the assumptions of the test, called "hypotheses"
- __Run__ the "hypothesis" on a computer

## What is a hypothesis?

The term "Hypothesis" used in Hypott means the following.

Recall predicate logic in mathematics. They generally have the following form.

- For any x0, x1, ..., satisfying some property --- (1)
- Some y0, y1, ... exists --- (2)
- Some logical equation (with them as variables) holds --- (3)

The purpose of software testing is to prove that such a predicates (hypothesis) really holds (or does not hold).

Therefore, the test code should always contain (1)-(3), but in reality, these three items are often described as a whole without clear distinction, and in such cases, the specification of the test itself is often not clear even by reading the test code.

Some programming languages support so-called formal methods or have powerful type systems (Lean, VDM, Coq, ...) as a way to represent such logical expressions in software code. These are also widely used nowadays, so using them is one way to express logic expressions.

Hypott's goal is to make it possible to express hypotheses as clearly as possible and as proof-like as possible (in fact, the proof itself is impossible, since there is no basis for proof).

### "For any ..."

One of the difficulties in expressing "hypotheses" in older programming languages is to distinguish between "arbitrary something" (item (1) of a hypothesis) and "existing something" (item (2) of a hypothesis).

This is because ordinary programming languages do not have a way to express "arbitrary something" as it is, so programmers are forced to write some concrete value, which is indistinguishable from the actually existing/specified value in the code.

In Hypott, such a precondition (1) is described by the function `forAny()`.

Then, the arbitrary value specified in `forAny()` is replaced by a random value when the software test is executed, thereby "imitating a proof". There is no need for the programmer to come up with an "arbitrary value" and replace it with a suitable value.

If there are only a finite number of (relatively few) choices of "arbitrary values" and all combinations of choices can be executed, then in a sense, a "proof by enumeration" has been achieved. Of course, in a realistic software code, this is not feasible.

## Hypott.forAny()

The `forAny()` function (for any anything) takes the following arguments.

- klass: KClass<T>
- variant: Map<String, Map> = emptyMap()
- where: Any? = null

Only klass is required, usually the class to be tested is passed.

```kotlin
val foo = hypott.forAny(Foo::class)
```

Each property of the resulting instance is assigned a suitable value based on a pseudo-random number.

It is also possible to set conditions on the value of a property (`variant`).

You can also assign a non-random, specific value (`where`).

## Hypott()

You can create a Hypott instance as follows.

```kotlin
val hypott = Hypott()
````

You can also pass `seed: Long` as an argument, which is the source of the pseudo-random numbers. The same `seed` can be used for reproducibility, since the value of the sequence of pseudo-random numbers is deterministic once the `seed` is determined. If you do not pass `seed`, then any suitable pseudo-random number is used.

You can also pass directly `kotlin.random.Random` to generate pseudo-random numbers. This is used when you want to put multiple instances of ## hypott in the same pseudo-random series.

## Tools similar to Hypott

There are many software tools similar to Hypott, called `faker`. Especially in JavaScript, there are many tools that generate human-readable values such as zip code, name, etc. Most of the `faker` tools are similar to `Hypott`, but they are not. The main purpose of most of the `fakers` is to generate a plausible value, not to test a hypothesis as Hypott does.

Property-based Testing is more test-centric than `faker` and others. Especially for Kotlin, [Kotest](https://kotest.io/) provides a Property-basde Testing tool, and it is one of the tools that you should consider using, given its track record and completeness. However, by default, for example, the tool performs 1,000 trials for each test. Whether this is acceptable or not depends on the user's world view.

The method of generating test data is one that has been used throughout the history of software engineering, starting with IBM in the 1970s.

Hypott's main goal, compared to `faker` and Property-base Testing, is "to be able to write (test) specifications". The generation of test data is only the result. That being said

- The ability to describe tests in a concise manner
- The ease with which test data can be generated

is a major feature of Hypott. The test code and the product code to be tested have a complementary relationship with each other as a meta.

