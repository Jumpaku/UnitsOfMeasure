package jumpaku.unitsofmeasure

import org.hamcrest.CustomTypeSafeMatcher
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.abs



class UnitTest {

    class IsCloseTo(val expected: Unit, val error: Double = 1e-10): CustomTypeSafeMatcher<Unit>("") {

        override fun matchesSafely(item: Unit): Boolean = expected.dimension == item.dimension &&
                expected.symbol == item.symbol &&
                abs(expected.coefficient - item.coefficient) <= error

    }

    val m = Symbol("m")
    val metre = Unit(m)
    val km = Symbol("km")
    val kilometre = km.bind(1e3, metre)

    val s = Symbol("s")
    val second = Unit(s)
    val h = Symbol("h")
    val hour = h.bind(3.6e3, second)

    val metrePerSecond = metre/second
    val kilometrePerHour = kilometre/hour

    val one = Unit()
    val two = Symbol.empty.bind(2.0, one)

    @Test
    fun testBind() {
        println("Bind")

        assertThat(metre.dimension, `is`(kilometre.dimension))
        assertThat(metre.symbol, `is`(m))
        assertThat(kilometre.symbol, `is`(km))
        assertEquals(metre.coefficient, 1.0, 1e-10)
        assertEquals(kilometre.coefficient, 1e3, 1e-10)

        assertThat(metrePerSecond.dimension, `is`(kilometrePerHour.dimension))
        assertThat(metrePerSecond.symbol, `is`(m/s))
        assertThat(kilometrePerHour.symbol, `is`(km/h))
        assertEquals(metrePerSecond.coefficient, 1.0, 1e-10)
        assertEquals(kilometrePerHour.coefficient, 1/3.6, 1e-10)

        assertThat(one.dimension, `is`(two.dimension))
        assertThat(one.symbol, `is`(Symbol.empty))
        assertThat(two.symbol, `is`(Symbol.empty))
        assertEquals(one.coefficient, 1.0, 1e-10)
        assertEquals(two.coefficient, 2.0, 1e-10)
    }

    @Test
    fun testTimes() {
        println("Times")

        val a = metre
        val b = second
        val c = one

        assertThat(a * (b * c), IsCloseTo((a * b) * c))
        assertThat(a * (c * b), IsCloseTo((a * c) * b))
        assertThat(b * (c * a), IsCloseTo((b * c) * a))
        assertThat(b * (a * c), IsCloseTo((b * a) * c))
        assertThat(c * (a * b), IsCloseTo((c * a) * b))
        assertThat(c * (b * a), IsCloseTo((c * b) * a))
        assertThat(a * (b * b), IsCloseTo((a * b) * b))
        assertThat(a * (a * b), IsCloseTo((a * a) * b))
        assertThat(a * (c * c), IsCloseTo((a * c) * c))
        assertThat(a * (a * c), IsCloseTo((a * a) * c))
        assertThat(b * (c * c), IsCloseTo((b * c) * c))
        assertThat(b * (b * c), IsCloseTo((b * b) * c))
        assertThat(b * (a * a), IsCloseTo((b * a) * a))
        assertThat(b * (b * a), IsCloseTo((b * b) * a))
        assertThat(c * (a * a), IsCloseTo((c * a) * a))
        assertThat(c * (c * a), IsCloseTo((c * c) * a))
        assertThat(c * (b * b), IsCloseTo((c * b) * b))
        assertThat(c * (c * b), IsCloseTo((c * c) * b))
        assertThat(a * (a * a), IsCloseTo((a * a) * a))
        assertThat(b * (b * b), IsCloseTo((b * b) * b))
        assertThat(c * (c * c), IsCloseTo((c * c) * c))

        assertThat(a * c, IsCloseTo(a))
        assertThat(c * a, IsCloseTo(a))
        assertThat(b * c, IsCloseTo(b))
        assertThat(c * b, IsCloseTo(b))
        assertThat(c * c, IsCloseTo(c))

        assertThat(a * b, IsCloseTo(b * a))
        assertThat(b * c, IsCloseTo(c * b))
        assertThat(c * a, IsCloseTo(a * c))
    }

    @Test
    fun testDiv() {
        println("Div")

        val a = metre
        val b = second
        val c = one

        assertThat((c / a) * a, IsCloseTo(c))
        assertThat(a * (c / a), IsCloseTo(c))
        assertThat((c / b) * b, IsCloseTo(c))
        assertThat(b * (c / b), IsCloseTo(c))
        assertThat((c / c) * c, IsCloseTo(c))
        assertThat(c * (c / c), IsCloseTo(c))
    }

    @Test
    fun testPow() {
        println("Pow")

        val a = metre
        val c = one

        assertThat(a.pow(3), IsCloseTo(a * a * a))
        assertThat(a.pow(2), IsCloseTo(a * a))
        assertThat(a.pow(1), IsCloseTo(a))
        assertThat(a.pow(0), IsCloseTo(c))
        assertThat(a.pow(-1), IsCloseTo(c / a))
        assertThat(a.pow(-2), IsCloseTo(c / a / a))
        assertThat(a.pow(-3), IsCloseTo(c / a / a / a))

        assertThat(c.pow(3), IsCloseTo(c))
        assertThat(c.pow(2), IsCloseTo(c))
        assertThat(c.pow(1), IsCloseTo(c))
        assertThat(c.pow(0), IsCloseTo(c))
        assertThat(c.pow(-1), IsCloseTo(c))
        assertThat(c.pow(-2), IsCloseTo(c))
        assertThat(c.pow(-3), IsCloseTo(c))
    }

    @Test
    fun testRename() {
        println("Rename")
        val kmPerH = Symbol("km/h")
        val a = kilometrePerHour.rename(kmPerH)

        assertThat(a.dimension, `is`(kilometrePerHour.dimension))
        assertThat(a.symbol, `is`(kmPerH))
        assertEquals(a.coefficient, kilometrePerHour.coefficient, 1e-10)
    }

}