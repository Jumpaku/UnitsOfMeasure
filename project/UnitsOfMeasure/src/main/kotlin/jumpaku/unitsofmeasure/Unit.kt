package jumpaku.unitsofmeasure

import kotlin.math.abs
import kotlin.math.pow

sealed class Unit {

    class Symbol(val name: String)

    abstract val coefficient: Double

    protected abstract val map: Map<Symbol, Int>

    abstract val dimension: Dimension

    infix fun pow(index: Int): Unit = Derived(
            coefficient.pow(index), map.mapValues { (_, v) -> v * index }, dimension.pow(index))

    operator fun times(u: Unit): Unit = Derived(
            coefficient*u.coefficient, simplifyMap(mergeMap(map, u.map)), dimension * u.dimension)

    operator fun div(d: Unit): Unit = times(d pow -1)

    override fun toString(): String = map.toList().run {
        when {
            isEmpty() -> "[$coefficient]"
            else -> joinToString("", if (coefficient == 1.0) "" else "[$coefficient]") { (s, i) ->
                "[${s.name}]" + if (i == 1) "" else "^$i"
            }
        }
    }

    class Converter(val coefficient: Double, val sourceUnit: Unit){
        fun into(targetSymbol: Symbol): Unit = Derived(sourceUnit.coefficient/coefficient, mapOf(targetSymbol to 1), sourceUnit.dimension)
    }

    companion object {

        fun convert(coefficient: Double, unit: Unit): Converter = Converter(coefficient, unit)

        private fun simplifyMap(m: Map<Symbol, Int>): Map<Symbol, Int> = m.filter { (_, v) -> v != 0 }

        private fun mergeMap(m0: Map<Symbol, Int>, m1: Map<Symbol, Int>): Map<Symbol, Int> =
                (m0.toList() + m1.toList())
                        .groupBy { it.first }
                        .mapValues { (_, v) -> v.map { it.second }.sum() }
                        .run { simplifyMap(toMap()) }

        fun equals(u0: Unit, u1: Unit, tolerance: Double): Boolean =
                abs(u0.coefficient - u1.coefficient) < tolerance &&
                        u0.map == u1.map &&
                        Dimension.equals(u0.dimension, u1.dimension)
    }

    class Dimensionless(override val coefficient: Double = 1.0): Unit() {
        override val dimension: Dimension = Dimension.Derived(mapOf())
        override val map: Map<Symbol, Int> = mapOf()
    }

    class Base(symbol: Symbol) : Unit() {
        override val coefficient: Double = 1.0
        override val map: Map<Symbol, Int> = mapOf(symbol to 1)
        override val dimension: Dimension.Base = Dimension.Base()
    }

    class Derived(
            override val coefficient: Double,
            override val map: Map<Symbol, Int>,
            override val dimension: Dimension) : Unit()
}