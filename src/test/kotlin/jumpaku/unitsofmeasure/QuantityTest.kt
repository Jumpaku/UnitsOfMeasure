package jumpaku.unitsofmeasure

import org.hamcrest.CustomTypeSafeMatcher
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.abs

class QuantityTest {

    class IsCloseTo(val expected: Quantity, val unit: Unit, val error: Double = 1e-10)
        : CustomTypeSafeMatcher<Quantity>("") {

        init {
            require(expected.dimension == unit.dimension) { "dimension mismatch" }
        }

        override fun matchesSafely(item: Quantity): Boolean =
                item.dimension == unit.dimension && abs(expected.valueIn(unit) - item.valueIn(unit)) <= error
    }

    val metre = Unit(Symbol("m"))
    val second = Unit(Symbol("s"))
    val metrePerSecond = metre/second
    val one = Unit()

    val l = Quantity(50.0, metre)
    val t = Quantity(10.0, second)
    val v = Quantity(5.0, metrePerSecond)
    val `2` = Quantity(2.0, one)

    val kilometre = Symbol("km").bind(1e3, metre)

    @Test
    fun testGetDimension() {
        println("GetDimension")

        assertThat(l.dimension, `is`(metre.dimension))
        assertThat(t.dimension, `is`(second.dimension))
        assertThat(v.dimension, `is`(metrePerSecond.dimension))
        assertThat(`2`.dimension, `is`(one.dimension))
    }

    @Test
    fun testValueIn() {
        println("ValueIn")

        assertEquals(l.valueIn(metre), 50.0, 1e-10)
        assertEquals(t.valueIn(second), 10.0, 1e-10)
        assertEquals(v.valueIn(metrePerSecond), 5.0, 1e-10)
        assertEquals(`2`.valueIn(one), 2.0, 1e-10)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testValueInThrow() {
        println("ValueInThrows")
        l.valueIn(second)
    }

    @Test
    fun testToStringIn() {
        println("ToStringIn")

        assertEquals(l.toStringIn(metre), "50.0[m]")
        assertEquals(t.toStringIn(second), "10.0[s]")
        assertEquals(v.toStringIn(metrePerSecond), "5.0[m*s^-1]")
        assertEquals(`2`.toStringIn(one), "2.0[1]")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testToStringInThrows() {
        println("ToStringInThrows")

        l.toStringIn(second)
    }

    @Test
    fun testTimesQuantity() {
        println("TimesQuantity")

        val a = l
        val b = t
        val c = Quantity(one)
        val m = metre
        val s = second
        val e = one

        assertThat(a * (b * c), IsCloseTo((a * b) * c, m*s*e))
        assertThat(a * (c * b), IsCloseTo((a * c) * b, m*e*s))
        assertThat(b * (c * a), IsCloseTo((b * c) * a, s*e*m))
        assertThat(b * (a * c), IsCloseTo((b * a) * c, s*m*e))
        assertThat(c * (a * b), IsCloseTo((c * a) * b, e*m*s))
        assertThat(c * (b * a), IsCloseTo((c * b) * a, e*s*m))
        assertThat(a * (b * b), IsCloseTo((a * b) * b, m*s*s))
        assertThat(a * (a * b), IsCloseTo((a * a) * b, m*m*s))
        assertThat(a * (c * c), IsCloseTo((a * c) * c, m*e*e))
        assertThat(a * (a * c), IsCloseTo((a * a) * c, m*m*e))
        assertThat(b * (c * c), IsCloseTo((b * c) * c, s*e*e))
        assertThat(b * (b * c), IsCloseTo((b * b) * c, s*s*e))
        assertThat(b * (a * a), IsCloseTo((b * a) * a, s*m*m))
        assertThat(b * (b * a), IsCloseTo((b * b) * a, s*s*m))
        assertThat(c * (a * a), IsCloseTo((c * a) * a, e*m*m))
        assertThat(c * (c * a), IsCloseTo((c * c) * a, e*e*m))
        assertThat(c * (b * b), IsCloseTo((c * b) * b, e*s*s))
        assertThat(c * (c * b), IsCloseTo((c * c) * b, e*e*s))
        assertThat(a * (a * a), IsCloseTo((a * a) * a, m*m*m))
        assertThat(b * (b * b), IsCloseTo((b * b) * b, s*s*s))
        assertThat(c * (c * c), IsCloseTo((c * c) * c, e*e*e))

        assertThat(a * c, IsCloseTo(a, m))
        assertThat(c * a, IsCloseTo(a, m))
        assertThat(b * c, IsCloseTo(b, s))
        assertThat(c * b, IsCloseTo(b, s))
        assertThat(c * c, IsCloseTo(c, e))

        assertThat(a * b, IsCloseTo(b * a, m*s))
        assertThat(b * c, IsCloseTo(c * b, s))
        assertThat(c * a, IsCloseTo(a * c, m))
    }

    @Test
    fun testTimes() {
        println("Times")

        val q = l
        val u = second
        val v = 2.0
        val m = metre
        val s = second
        val e = one

        assertThat(q*u, IsCloseTo(Quantity(50.0, m*s), m*s))
        assertThat(q*v, IsCloseTo(Quantity(100.0, m*e), m*e))

        assertThat(u*q, IsCloseTo(Quantity(50.0, s*m), s*m))
        assertThat(u*v, IsCloseTo(Quantity(2.0, s*e), s*e))

        assertThat(v*q, IsCloseTo(Quantity(100.0, e*m), e*m))
        assertThat(v*u, IsCloseTo(Quantity(2.0, e*s), e*s))
    }

    @Test
    fun testDivQuantity() {
        println("DivQuantity")

        val a = l
        val b = t
        val c = Quantity(one)
        val e = one

        assertThat((c / a) * a, IsCloseTo(c, e))
        assertThat(a * (c / a), IsCloseTo(c, e))
        assertThat((c / b) * b, IsCloseTo(c, e))
        assertThat(b * (c / b), IsCloseTo(c, e))
        assertThat((c / c) * c, IsCloseTo(c, e))
        assertThat(c * (c / c), IsCloseTo(c, e))
    }

    @Test
    fun testDiv() {
        println("Div")

        val q = l
        val u = second
        val v = 2.0
        val m = metre
        val s = second
        val e = one

        assertThat(q/u, IsCloseTo(Quantity(50.0, m/s), m/s))
        assertThat(q/v, IsCloseTo(Quantity(25.0, m/e), m/e))

        assertThat(u/q, IsCloseTo(Quantity(0.02, s/m), s/m))
        assertThat(u/v, IsCloseTo(Quantity(0.5, s/e), s/e))

        assertThat(v/q, IsCloseTo(Quantity(0.04, e/m), e/m))
        assertThat(v/u, IsCloseTo(Quantity(2.0, e/s), e/s))
    }

    @Test
    fun testPow() {
        println("Pow")

        val a = l
        val c = Quantity(one)
        val m = metre
        val e = one

        assertThat(a.pow(3), IsCloseTo(a * a * a, m.pow(3)))
        assertThat(a.pow(2), IsCloseTo(a * a, m.pow(2)))
        assertThat(a.pow(1), IsCloseTo(a, m))
        assertThat(a.pow(0), IsCloseTo(c, e))
        assertThat(a.pow(-1), IsCloseTo(c / a, m.pow(-1)))
        assertThat(a.pow(-2), IsCloseTo(c / a / a, m.pow(-2)))
        assertThat(a.pow(-3), IsCloseTo(c / a / a / a, m.pow(-3)))

        assertThat(c.pow(3), IsCloseTo(c, e))
        assertThat(c.pow(2), IsCloseTo(c, e))
        assertThat(c.pow(1), IsCloseTo(c, e))
        assertThat(c.pow(0), IsCloseTo(c, e))
        assertThat(c.pow(-1), IsCloseTo(c, e))
        assertThat(c.pow(-2), IsCloseTo(c, e))
        assertThat(c.pow(-3), IsCloseTo(c, e))
    }

    @Test
    fun testPlus() {
        println("Plus")

        val a = l
        val b = 0.05*kilometre
        val c = 0.0*metre
        val m = metre

        assertThat(+a, IsCloseTo(50.0*m, m))

        assertThat(a + (b + c), IsCloseTo((a + b) + c, m))
        assertThat(a + (c + b), IsCloseTo((a + c) + b, m))
        assertThat(b + (c + a), IsCloseTo((b + c) + a, m))
        assertThat(b + (a + c), IsCloseTo((b + a) + c, m))
        assertThat(c + (a + b), IsCloseTo((c + a) + b, m))
        assertThat(c + (b + a), IsCloseTo((c + b) + a, m))
        assertThat(a + (b + b), IsCloseTo((a + b) + b, m))
        assertThat(a + (a + b), IsCloseTo((a + a) + b, m))
        assertThat(a + (c + c), IsCloseTo((a + c) + c, m))
        assertThat(a + (a + c), IsCloseTo((a + a) + c, m))
        assertThat(b + (c + c), IsCloseTo((b + c) + c, m))
        assertThat(b + (b + c), IsCloseTo((b + b) + c, m))
        assertThat(b + (a + a), IsCloseTo((b + a) + a, m))
        assertThat(b + (b + a), IsCloseTo((b + b) + a, m))
        assertThat(c + (a + a), IsCloseTo((c + a) + a, m))
        assertThat(c + (c + a), IsCloseTo((c + c) + a, m))
        assertThat(c + (b + b), IsCloseTo((c + b) + b, m))
        assertThat(c + (c + b), IsCloseTo((c + c) + b, m))
        assertThat(a + (a + a), IsCloseTo((a + a) + a, m))
        assertThat(b + (b + b), IsCloseTo((b + b) + b, m))
        assertThat(c + (c + c), IsCloseTo((c + c) + c, m))

        assertThat(a + c, IsCloseTo(a, m))
        assertThat(c + a, IsCloseTo(a, m))
        assertThat(b + c, IsCloseTo(b, m))
        assertThat(c + b, IsCloseTo(b, m))
        assertThat(c + c, IsCloseTo(c, m))

        assertThat(a + b, IsCloseTo(b + a, m))
        assertThat(b + c, IsCloseTo(c + b, m))
        assertThat(c + a, IsCloseTo(a + c, m))
    }

    @Test
    fun testMinus() {
        println("Minus")

        val a = l
        val b = 0.05*kilometre
        val c = 0.0*metre
        val m = metre

        assertThat(-a, IsCloseTo((-50.0)*m, m))

        assertThat((c - a) + a, IsCloseTo(c, m))
        assertThat(a + (c - a), IsCloseTo(c, m))
        assertThat((c - b) + b, IsCloseTo(c, m))
        assertThat(b + (c - b), IsCloseTo(c, m))
        assertThat((c - c) + c, IsCloseTo(c, m))
        assertThat(c + (c - c), IsCloseTo(c, m))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testPlusThrows() {
        println("PlusThrows")
        l + t
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMinusThrows() {
        println("MinusThrows")
        l - t
    }
}