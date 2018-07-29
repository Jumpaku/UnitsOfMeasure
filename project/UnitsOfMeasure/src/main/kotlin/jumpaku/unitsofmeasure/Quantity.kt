package jumpaku.unitsofmeasure

class Quantity(value: Double, unit: Unit = Unit.Dimensionless(), val tolerance: Double = 1.0e-10) {

    val value: Double = value*unit.coefficient

    val unit: Unit = unit/Unit.Dimensionless(unit.coefficient)

    operator fun times(q: Quantity): Quantity = Quantity(value * q.value, unit * q.unit)

    operator fun times(v: Double): Quantity = Quantity(value * v, unit)

    operator fun times(u: Unit): Quantity = Quantity(value, unit * u)

    operator fun div(q: Quantity): Quantity = Quantity(value / q.value, unit / q.unit)

    operator fun div(v: Double): Quantity = Quantity(value / v, unit)

    operator fun div(u: Unit): Quantity = Quantity(value, unit / u)

    operator fun unaryPlus(): Quantity = this

    operator fun plus(q: Quantity): Quantity {
        require(Unit.equals(unit, q.unit, tolerance)) { "unit mismatched" }
        return Quantity(value + q.value, unit)
    }

    operator fun unaryMinus(): Quantity = times(-1.0)

    operator fun minus(q: Quantity): Quantity = plus(-q)

    operator fun compareTo(q: Quantity): Int {
        require(unit == q.unit) { "unit mismatched" }
        return value.compareTo(q.value)
    }

    override fun toString(): String = "$value $unit"

    fun convertInto(unit: Unit): Quantity {
        require(Dimension.equals(this.unit.dimension, unit.dimension)) { "dimension mismatched" }
        return Quantity(value * unit.coefficient, unit/Unit.Dimensionless(unit.coefficient))
    }
}

operator fun Double.times(q: Quantity): Quantity = q*this

operator fun Double.times(u: Unit): Quantity = Quantity(this, u)

operator fun Double.div(q: Quantity): Quantity = Quantity(this / q.value, q.unit.pow(-1))

operator fun Double.div(u: Unit): Quantity = Quantity(this, u.pow(-1))

