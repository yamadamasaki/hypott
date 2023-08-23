@file:DependsOn("org.apache.commons:commons-statistics-distribution:1.0")
@file:DependsOn("org.apache.commons:commons-rng-simple:1.5")
import org.apache.commons.rng.RestorableUniformRandomProvider
import org.apache.commons.rng.simple.RandomSource
import org.apache.commons.statistics.distribution.DiscreteDistribution
import org.apache.commons.statistics.distribution.PoissonDistribution

val distribution: PoissonDistribution = PoissonDistribution.of(5.0)
val generator: RestorableUniformRandomProvider = RandomSource.JDK.create(java.util.Date().time)
val sampler: DiscreteDistribution.Sampler = distribution.createSampler(generator)
val list = sampler.samples(100000L).toArray().toList()
val frequency = list.groupingBy {it}.eachCount().toList().sortedBy { it.first }

println(frequency)
println(distribution.mean)
println(distribution.supportLowerBound)
println(distribution.supportUpperBound)
println(distribution.variance)
