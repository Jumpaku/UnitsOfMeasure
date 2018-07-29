package jumpaku.unitsofmeasure

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.abs

class QuantityTest {

    fun Quantity.isCloseTo(q: Quantity, error: Double): Boolean =
            unit.isCloseTo(q.unit, error) && abs(value - q.value) <= error

    val metre = Unit(Symbol("m"))
    val second = Unit(Symbol("s"))
    val metrePerSecond = metre/second

    val l = Quantity(50.0, metre)
    val t = Quantity(10.0, second)
    val v = Quantity(5.0, metrePerSecond)

    val kilometre = Unit.convert(1e-3, metre).into(Symbol("km"))

    @Test
    fun testProperties() {
        println("Properties")

        assertEquals(l.value, 50.0, 1e-10)
        assertEquals(t.value, 10.0, 1e-10)
        assertEquals(v.value, 5.0, 1e-10)

        assertTrue(l.unit.isCloseTo(metre, 1e-10))
        assertTrue(t.unit.isCloseTo(second, 1e-10))
        assertTrue(v.unit.isCloseTo(metrePerSecond, 1e-10))
    }

    @Test
    fun testTimes() {
        println("Times")

        assertTrue((50.0*metre).isCloseTo(l, 1e-10))
        assertTrue((1.0*l).isCloseTo(l, 1e-10))
        assertTrue((metre*50.0).isCloseTo(l, 1e-10))
        assertTrue((second*(10.0*v)).isCloseTo(l, 1e-10))
        assertTrue((l*1.0).isCloseTo(l, 1e-10))
        assertTrue(((10.0*v)*second).isCloseTo(l, 1e-10))
        assertTrue((v*t).isCloseTo(l, 1e-10))
    }

    @Test
    fun testDiv() {
        println("Div")

        val secondPerMetre = second/metre

        assertTrue((5.0/(secondPerMetre)).isCloseTo(v, 1e-10))
        assertTrue((50.0/(10.0*secondPerMetre)).isCloseTo(v, 1e-10))
        assertTrue((metrePerSecond/0.2).isCloseTo(v, 1e-10))
        assertTrue((metre/(0.2*second)).isCloseTo(v, 1e-10))
        assertTrue((v/1.0).isCloseTo(v, 1e-10))
        assertTrue((l/(10.0*second)).isCloseTo(v, 1e-10))
        assertTrue((l/t).isCloseTo(v, 1e-10))
    }

    @Test
    fun testCompareTo() {
        println("CompareTo")

        assertEquals(l.compareTo(60.0*metre), -1)
        assertEquals(l.compareTo(50.0*metre), 0)
        assertEquals(l.compareTo(40.0*metre), 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCompareToMismatchUnit() {
        println("CompareToMismatchUnit")

        val hour = Unit.convert(3.6e3, second).into(Symbol("h"))
        assertEquals(l.compareTo(60.0*hour), 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCompareToMismatchUnitDimension() {
        println("CompareToMismatchUnitDimension")

        assertEquals(l.compareTo(60.0*second), 0)
    }

    @Test
    fun testConvertInto() {
        println("ConvertInto")

        assertTrue(l.convertInto(kilometre).isCloseTo(0.05*kilometre, 1e-10))
    }

    @Test
    fun testToString() {
        println("ToString")
        assertEquals(v.toString(), "5.0[m][s]^-1")
    }


    val a = 100.0*metre
    val b = 200.0*metre
    val c = 200.0*second
    val d = 200.0*kilometre

    @Test
    fun testPlus() {
        println("Plus")
        assertTrue((a+b).isCloseTo(300.0*metre, 1e-10))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testPlusMismatchUnit() {
        println("PlusMismatchUnit")
        assertTrue((a+c).isCloseTo(300.0*metre, 1e-10))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testPlusMismatchUnitDimension() {
        println("PlusMismatchUnitDimension")
        assertTrue((a+d).isCloseTo(300.0*metre, 1e-10))
    }

    @Test
    fun testMinus() {
        println("Minus")
        assertTrue((a-b).isCloseTo(-100.0*metre, 1e-10))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMinusMismatchUnit() {
        println("MinusMismatchUnit")
        assertTrue((a-c).isCloseTo(-100.0*metre, 1e-10))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMinusMismatchUnitDimension() {
        println("MinusMismatchUnitDimension")
        assertTrue((a-d).isCloseTo(-100.0*metre, 1e-10))
    }
}
