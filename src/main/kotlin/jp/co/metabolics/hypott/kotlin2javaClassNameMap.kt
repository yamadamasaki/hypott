package jp.co.metabolics.hypott

val kotlin2javaClassNameMap = mapOf(
  // primitives
  "kotlin.Boolean" to "java.lang.Boolean",
  "kotlin.Char" to "java.lang.Character",
  "kotlin.Byte" to "java.lang.Byte",
  "kotlin.Short" to "java.lang.Short",
  "kotlin.Int" to "java.lang.Integer",
  "kotlin.Float" to "java.lang.Float",
  "kotlin.Long" to "java.lang.Long",
  "kotlin.Double" to "java.lang.Double",
  // collections
  "kotlin.collections.Iterable" to "java.lang.Iterable",
  "kotlin.collections.Iterator" to "java.util.Iterator",
  "kotlin.collections.Collection" to "java.util.Collection",
  "kotlin.collections.List" to "java.util.List",
  "kotlin.collections.Set" to "java.util.Set",
  "kotlin.collections.ListIterator" to "java.util.ListIterator",
  "kotlin.collections.Map" to "java.util.Map",
  "kotlin.collections.Map.Entry" to "java.util.Map.Entry",
  // arrays
  // "kotlin.BooleanArray" to "java.lang.BooleanArray",
  // "kotlin.CharArray" to "java.lang.CharArray",
  // "kotlin.ByteArray" to "java.lang.ByteArray",
  // "kotlin.ShortArray" to "java.lang.ShortArray",
  // "kotlin.IntArray" to "java.lang.IntArray",
  // "kotlin.FloatArray" to "java.lang.FloatArray",
  // "kotlin.LongArray" to "java.lang.LongArray",
  // "kotlin.DoubleArray" to "java.lang.DoubleArray",
  "kotlin.Array" to "java.lang.reflect.Array",
  // miscs
  // "kotlin.Any" to "java.lang.Any",
  "kotlin.String" to "java.lang.String",
  "kotlin.CharSequence" to "java.lang.CharSequence",
  "kotlin.Throwable" to "java.lang.Throwable",
  "kotlin.Cloneable" to "java.lang.Cloneable",
  "kotlin.Number" to "java.lang.Number",
  "kotlin.Comparable" to "java.lang.Comparable",
  "kotlin.Enum" to "java.lang.Enum",
  "kotlin.Annotation" to "java.lang.annotation.Annotation",
)

