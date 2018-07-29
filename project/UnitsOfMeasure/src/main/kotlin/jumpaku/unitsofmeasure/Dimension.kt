package jumpaku.unitsofmeasure

sealed class Dimension {

    protected class Element

    protected abstract val map: Map<Element, Int>

    protected class Base: Dimension() {
        override val map = mapOf(Element() to 1)
    }

    protected class Derived(map: Map<Element, Int>): Dimension() {

        constructor(vararg dimensions: Dimension): this(
                dimensions.map { it.map }
                        .fold(mapOf<Element, Int>()){ acc, m -> mergeMap(acc, m) }
                        .let { simplifyMap(it) })

        override val map: Map<Element, Int> = simplifyMap(map)
    }

    infix fun pow(index: Int): Dimension = Derived(map.mapValues { (_, v) -> v*index })

    operator fun times(d: Dimension): Dimension = Derived(simplifyMap(mergeMap(map, d.map)))

    operator fun div(d: Dimension): Dimension = times(d pow -1)

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Dimension -> false
        else -> map == other.map
    }

    override fun hashCode(): Int = map.hashCode()


    companion object {

        operator fun invoke(): Dimension = Base()

        fun dimensionless(): Dimension = Derived()

        fun compose(dimension: Dimension, vararg dimensions: Dimension): Dimension = Derived(dimension, *dimensions)

        private fun simplifyMap(m: Map<Element, Int>): Map<Element, Int> = m.filter { (_, v) -> v != 0 }

        private fun mergeMap(m0: Map<Element, Int>, m1: Map<Element, Int>): Map<Element, Int> =
                (m0.toList() + m1.toList())
                        .groupBy { it.first }
                        .mapValues { (_, v) -> v.map { it.second }.sum() }
                        .run { simplifyMap(toMap()) }
    }
}