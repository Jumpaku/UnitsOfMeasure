package jumpaku.unitsofmeasure

import kotlin.math.abs
import kotlin.math.pow

class Symbol(val name: String)

sealed class Unit {

    class Converter(val coefficient: Double, val sourceUnit: Unit){
        fun into(targetSymbol: Symbol): Unit =
                Derived(sourceUnit.coefficient/coefficient, mapOf(targetSymbol to 1), sourceUnit.dimension)
    }

    abstract val coefficient: Double

    protected abstract val map: Map<Symbol, Int>

    abstract val dimension: Dimension

    infix fun pow(index: Int): Unit = Derived(
            coefficient.pow(index), simplifyMap(map.mapValues { (_, v) -> v * index }), dimension.pow(index))

    operator fun times(u: Unit): Unit = Derived(
            coefficient*u.coefficient, simplifyMap(mergeMap(map, u.map)), dimension * u.dimension)

    operator fun div(u: Unit): Unit = times(u pow -1)

    fun isCloseTo(other: Unit, error: Double): Boolean =
        abs(coefficient - other.coefficient) <= error &&
                map == other.map &&
                dimension == other.dimension

    companion object {

        operator fun invoke(symbol: Symbol): Unit = Base(symbol)

        operator fun invoke(coefficient: Double = 1.0): Unit = Derived(coefficient, mapOf(), Dimension.dimensionless())

        fun convert(coefficient: Double, unit: Unit): Converter = Converter(coefficient, unit)

        private fun simplifyMap(m: Map<Symbol, Int>): Map<Symbol, Int> = m.filter { (_, v) -> v != 0 }

        private fun mergeMap(m0: Map<Symbol, Int>, m1: Map<Symbol, Int>): Map<Symbol, Int> =
                (m0.toList() + m1.toList())
                        .groupBy { it.first }
                        .mapValues { (_, v) -> v.map { it.second }.sum() }
                        .run { simplifyMap(toMap()) }

        internal fun stringExpression(unit: Unit): String = when {
            unit.map.isNotEmpty() -> unit.map.toList().joinToString("") { (s, i) ->
                "[${s.name}]" + if (i == 1) "" else "^$i"
            }
            else -> ""
        }
    }

    protected class Base(symbol: Symbol) : Unit() {
        override val coefficient: Double = 1.0
        override val map: Map<Symbol, Int> = mapOf(symbol to 1)
        override val dimension: Dimension = Dimension()
    }

    protected class Derived(
            override val coefficient: Double,
            map: Map<Symbol, Int>,
            override val dimension: Dimension) : Unit() {
        override val map: Map<Symbol, Int> = simplifyMap(map)
    }
}