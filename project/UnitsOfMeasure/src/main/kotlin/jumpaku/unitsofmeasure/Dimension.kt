package jumpaku.unitsofmeasure

sealed class Dimension {

    protected abstract val map: Map<Base, Int>

    class Base: Dimension() {
        override val map = mapOf(this to 1)
    }

    class Derived(map: Map<Base, Int>): Dimension() {
        override val map: Map<Base, Int> = simplifyMap(map)
    }

    infix fun pow(index: Int): Dimension = Derived(map.mapValues { (_, v) -> v*index })

    operator fun times(d: Dimension): Dimension = Derived(simplifyMap(mergeMap(map, d.map)))

    operator fun div(d: Dimension): Dimension = times(d pow -1)

    companion object {

        internal fun simplifyMap(m: Map<Base, Int>): Map<Base, Int> = m.filter { (_, v) -> v != 0 }

        internal fun mergeMap(m0: Map<Base, Int>, m1: Map<Base, Int>): Map<Base, Int> =
                (m0.toList() + m1.toList())
                        .groupBy { it.first }
                        .mapValues { (_, v) -> v.map { it.second }.sum() }
                        .run { simplifyMap(toMap()) }

        fun equals(d0: Dimension, d1: Dimension): Boolean = d0.map == d1.map
    }
}