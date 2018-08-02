package jumpaku.unitsofmeasure

import kotlin.math.pow


class Quantity(value: Double, unit: Unit) {

    constructor(value: Double): this(value, Unit())

    constructor(unit: Unit): this(1.0, unit)

    private val valueInBase: Double = value*unit.coefficient

    private val baseUnit: Unit = unit / Unit(unit.coefficient) // baseUnit.coefficient must equal to 1.0

    val dimension: Dimension = baseUnit.dimension

    init {
        assert(baseUnit.coefficient == 1.0) { "baseUnit.coefficient == ${baseUnit.coefficient}" }
    }

    fun valueIn(unit: Unit): Double {
        require(dimension == unit.dimension) { "dimension mismatch" }
        return valueInBase / unit.coefficient
    }

    override fun toString(): String = toStringIn(baseUnit)

    fun toStringIn(unit: Unit): String {
        require(dimension == unit.dimension) { "dimension mismatch" }
        return "${valueInBase / unit.coefficient}[${if (dimension == Dimension.ONE) "1" else unit.symbol.symbolString}]"
    }

    fun pow(i: Int): Quantity = Quantity(valueInBase.pow(i), baseUnit.pow(i))

    operator fun times(q: Quantity): Quantity = Quantity(valueInBase*q.valueInBase, baseUnit*q.baseUnit)
    operator fun times(u: Unit): Quantity = times(Quantity(u))
    operator fun times(v: Double): Quantity = times(Quantity(v))

    operator fun div(q: Quantity): Quantity = times(q.pow(-1))
    operator fun div(u: Unit): Quantity = times(u.pow(-1))
    operator fun div(v: Double): Quantity = times(v.pow(-1))

    operator fun unaryPlus(): Quantity = this
    operator fun plus(q: Quantity): Quantity {
        require(dimension == q.dimension) { "dimension mismatch" }
        return Quantity(valueInBase+q.valueInBase, baseUnit)
    }
    operator fun unaryMinus(): Quantity = times(-1.0)
    operator fun minus(q: Quantity): Quantity = plus(q.unaryMinus())
}

operator fun Unit.times(q: Quantity): Quantity = q.times(this)
operator fun Unit.times(v: Double): Quantity = Quantity(v, this)
operator fun Double.times(q: Quantity): Quantity = q.times(this)
operator fun Double.times(u: Unit): Quantity = u.times(this)

operator fun Unit.div(q: Quantity): Quantity = times(q.pow(-1))
operator fun Unit.div(v: Double): Quantity = times(v.pow(-1))
operator fun Double.div(q: Quantity): Quantity = times(q.pow(-1))
operator fun Double.div(u: Unit): Quantity = times(u.pow(-1))
