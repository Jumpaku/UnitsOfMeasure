package jumpaku.unitsofmeasure

import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class UnitTest {

    val tolerance = 1e-10

    val metre = Unit(Symbol("m"))
    val kilometre = Unit.convert(1e3, metre).into(Symbol("km"))

    val second = Unit(Symbol("s"))
    val hour = Unit.convert(3.6e3, second).into(Symbol("h"))

    val metrePerSecond = metre/second
    val kilometrePerHour = kilometre/hour

    val one = Unit()
    val two = Unit(2.0)

    @Test
    fun testProperties() {
        println("Properties")

        assertThat(metre.dimension, `is`(kilometre.dimension))
        assertEquals(metre.coefficient, 1.0, 1e-10)
        assertEquals(kilometre.coefficient, 1e-3, 1e-10)

        assertThat(metrePerSecond.dimension, `is`(kilometrePerHour.dimension))
        assertEquals(metrePerSecond.coefficient, 1.0, 1e-10)
        assertEquals(kilometrePerHour.coefficient, 3.6, 1e-10)

        assertThat(one.dimension, `is`(two.dimension))
        assertEquals(one.coefficient, 1.0, 1e-10)
        assertEquals(two.coefficient, 2.0, 1e-10)
    }

    @Test
    fun testIsCloseTo() {
        println("IsCloseTo")

        assertTrue(kilometre.isCloseTo(Unit(1.0)*kilometre, tolerance))
        assertFalse(kilometre.isCloseTo(Unit(1e3)*metre, tolerance))
        assertFalse(kilometre.isCloseTo(Unit(1089.0)*metre, tolerance))
        assertFalse(kilometre.isCloseTo(Unit(1089.0)*kilometre, tolerance))
        assertFalse(kilometre.isCloseTo(Unit(1.0)*second, tolerance))

    }

    @Test
    fun testTimes() {
        println("Times")

        assertTrue(((metre*metre)*second).isCloseTo(metre*(metre*second), tolerance))
        assertTrue(((metre*metre)*second).isCloseTo(metre*(second*metre), tolerance))
        assertTrue(((metre*metre)*second).isCloseTo((metre*second)*metre, tolerance))
        assertTrue(((metre*metre)*second).isCloseTo(second*(metre*metre), tolerance))
        assertTrue(((metre*metre)*second).isCloseTo((second*metre)*metre, tolerance))
    }

    @Test
    fun testDiv() {
        println("Div")

        assertTrue(((metre*metre)/second).isCloseTo(metre*(metre/second), tolerance))
        assertTrue(((metre*metre)/second).isCloseTo((metre/second)*metre, tolerance))
        assertTrue(((metre*metre)/second).isCloseTo((one/second)*metre*metre, tolerance))
    }

    @Test
    fun testPow() {
        println("Pow")

        assertTrue(metre.pow(-2).isCloseTo(one/metre/metre, tolerance))
        assertTrue(metre.pow(-1).isCloseTo(one/metre, tolerance))
        assertTrue(metre.pow(0).isCloseTo(one, tolerance))
        assertTrue(metre.pow(1).isCloseTo(metre, tolerance))
        assertTrue(metre.pow(2).isCloseTo(metre*metre, tolerance))
    }
}
