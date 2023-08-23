@file:DependsOn("org.apache.commons:commons-statistics-distribution:1.0")
import org.apache.commons.statistics.distribution.TDistribution
println("FOOBAR")
val t: TDistribution = TDistribution.of(29.0)
val lowerTail: Double = t.cumulativeProbability(-2.656)
val upperTail: Double = t.survivalProbability(2.75)
println(": $lowerTail, $upperTail")
