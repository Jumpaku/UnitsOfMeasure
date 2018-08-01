package jumpaku.unitofmeasure

import kotlin.math.pow

class Unit private constructor(val coefficient: Double, val symbol: Symbol, val dimension: Dimension) {

    constructor(symbol: Symbol): this(1.0, symbol, Dimension())

    constructor(coefficient: Double = 1.0): this(coefficient, Symbol.empty, Dimension.ONE)

    fun pow(i: Int): Unit = Unit(coefficient.pow(i), symbol.pow(i), dimension.pow(i))

    operator fun times(u: Unit): Unit =
            Unit(coefficient * u.coefficient, symbol * u.symbol, dimension * u.dimension)

    operator fun div(u: Unit): Unit = times(u.pow(-1))

    internal class Convert(val coefficient: Double, val source: Unit) {

        fun into(targetSymbol: Symbol): Unit =
                Unit(coefficient * source.coefficient, targetSymbol, source.dimension)
    }

    fun rename(symbol: Symbol): Unit = symbol.bind(1.0, this)
}

fun Symbol.bind(coefficient: Double, source: Unit): Unit = Unit.Convert(coefficient, source).into(this)
