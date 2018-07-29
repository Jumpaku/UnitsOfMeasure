package jumpaku.unitsofmeasure


class Quantity(val value: Double, unit: Unit = Unit()) {

    val unit: Unit = unit/Unit(unit.coefficient)

    operator fun times(q: Quantity): Quantity = Quantity(value * q.value, unit * q.unit)

    operator fun times(v: Double): Quantity = Quantity(value * v, unit)

    operator fun times(u: Unit): Quantity = Quantity(value, unit * u)

    operator fun div(q: Quantity): Quantity = Quantity(value / q.value, unit / q.unit)

    operator fun div(v: Double): Quantity = Quantity(value / v, unit)

    operator fun div(u: Unit): Quantity = Quantity(value, unit / u)

    operator fun unaryPlus(): Quantity = this

    operator fun plus(q: Quantity): Quantity {
        require(unit.isCloseTo(q.unit, 0.0)) { "unit mismatched" }
        return Quantity(value + q.value, unit)
    }

    operator fun unaryMinus(): Quantity = times(-1.0)

    operator fun minus(q: Quantity): Quantity = plus(-q)

    operator fun compareTo(q: Quantity): Int {
        require(unit.isCloseTo(q.unit, 0.0)) { "unit mismatched" }
        return value.compareTo(q.value)
    }

    override fun toString(): String = "$value${Unit.stringExpression(unit)}"

    fun convertInto(unit: Unit): Quantity {
        require(this.unit.dimension == unit.dimension) { "dimension mismatched" }
        return Quantity(value/unit.coefficient, unit/Unit(unit.coefficient))
    }
}

operator fun Double.times(q: Quantity): Quantity = q*this

operator fun Double.times(u: Unit): Quantity = Quantity(this, u)

operator fun Unit.times(v: Double): Quantity = Quantity(v, this)

operator fun Unit.times(q: Quantity): Quantity = Quantity(q.value, this*q.unit)

operator fun Double.div(q: Quantity): Quantity = Quantity(this / q.value, q.unit.pow(-1))

operator fun Double.div(u: Unit): Quantity = Quantity(this, u.pow(-1))

operator fun Unit.div(v: Double): Quantity = Quantity(1.0/v, this)

operator fun Unit.div(q: Quantity): Quantity = Quantity(1.0/q.value, this/q.unit)
