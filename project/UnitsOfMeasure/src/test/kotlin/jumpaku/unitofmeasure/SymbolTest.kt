package jumpaku.unitofmeasure

import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class SymbolTest {

    val a = Symbol("a")
    val b = Symbol("b")
    val c = Symbol.empty
    val d = Symbol("a")

    @Test
    fun testEquals() {
        println("Equals")

        assertTrue(a == a)
        assertFalse(a == b)
        assertFalse(a == c)
        assertFalse(a == d)
        assertFalse(b == a)
        assertTrue(b == b)
        assertFalse(b == c)
        assertFalse(b == d)
        assertFalse(c == a)
        assertFalse(c == b)
        assertTrue(c == c)
        assertFalse(c == d)
        assertFalse(d == a)
        assertFalse(d == b)
        assertFalse(d == c)
        assertTrue(d == d)
    }

    @Test
    fun testTimes() {
        println("Times")

        assertThat(a * (b * c), `is`((a * b) * c))
        assertThat(a * (c * b), `is`((a * c) * b))
        assertThat(b * (c * a), `is`((b * c) * a))
        assertThat(b * (a * c), `is`((b * a) * c))
        assertThat(c * (a * b), `is`((c * a) * b))
        assertThat(c * (b * a), `is`((c * b) * a))
        assertThat(a * (b * b), `is`((a * b) * b))
        assertThat(a * (a * b), `is`((a * a) * b))
        assertThat(a * (c * c), `is`((a * c) * c))
        assertThat(a * (a * c), `is`((a * a) * c))
        assertThat(b * (c * c), `is`((b * c) * c))
        assertThat(b * (b * c), `is`((b * b) * c))
        assertThat(b * (a * a), `is`((b * a) * a))
        assertThat(b * (b * a), `is`((b * b) * a))
        assertThat(c * (a * a), `is`((c * a) * a))
        assertThat(c * (c * a), `is`((c * c) * a))
        assertThat(c * (b * b), `is`((c * b) * b))
        assertThat(c * (c * b), `is`((c * c) * b))
        assertThat(a * (a * a), `is`((a * a) * a))
        assertThat(b * (b * b), `is`((b * b) * b))
        assertThat(c * (c * c), `is`((c * c) * c))

        assertThat(a * c, `is`(a))
        assertThat(c * a, `is`(a))
        assertThat(b * c, `is`(b))
        assertThat(c * b, `is`(b))
        assertThat(c * c, `is`(c))

        assertThat(a * b, `is`(b * a))
        assertThat(b * c, `is`(c * b))
        assertThat(c * a, `is`(a * c))
    }

    @Test
    fun testDiv() {
        println("Div")

        assertThat((c / a) * a, `is`(c))
        assertThat(a * (c / a), `is`(c))
        assertThat((c / b) * b, `is`(c))
        assertThat(b * (c / b), `is`(c))
        assertThat((c / c) * c, `is`(c))
        assertThat(c * (c / c), `is`(c))
    }

    @Test
    fun testPow() {
        println("Pow")

        assertThat(a.pow(3), `is`(a * a * a))
        assertThat(a.pow(2), `is`(a * a))
        assertThat(a.pow(1), `is`(a))
        assertThat(a.pow(0), `is`(c))
        assertThat(a.pow(-1), `is`(c / a))
        assertThat(a.pow(-2), `is`(c / a / a))
        assertThat(a.pow(-3), `is`(c / a / a / a))

        assertThat(c.pow(3), `is`(c))
        assertThat(c.pow(2), `is`(c))
        assertThat(c.pow(1), `is`(c))
        assertThat(c.pow(0), `is`(c))
        assertThat(c.pow(-1), `is`(c))
        assertThat(c.pow(-2), `is`(c))
        assertThat(c.pow(-3), `is`(c))
    }


    @Test
    fun testGetSymbolString() {
        println("GetSymbolString")

        assertEquals(a.symbolString, "a")
        assertEquals(b.symbolString, "b")
        assertEquals(c.symbolString, "")
        assertEquals(d.symbolString, "a")

        assertEquals((a * b).symbolString, "a*b")
        assertEquals((a / b).symbolString, "a*b^-1")
        assertEquals((b / a).symbolString, "b*a^-1")
        assertEquals((a * c).symbolString, "a")
        assertEquals((a / c).symbolString, "a")
        assertEquals((c / a).symbolString, "a^-1")

        assertEquals((a.pow(-2)).symbolString, "a^-2")
        assertEquals((a.pow(-1)).symbolString, "a^-1")
        assertEquals((a.pow(0)).symbolString, "")
        assertEquals((a.pow(1)).symbolString, "a")
        assertEquals((a.pow(2)).symbolString, "a^2")
    }
}
