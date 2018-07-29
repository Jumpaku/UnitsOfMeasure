package jumpaku.unitsofmeasure

import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class DimensionTest {

    val length = Dimension()
    val area = Dimension.compose(length, length)
    val volume = Dimension.compose(length, length, length)
    val dimensionless = Dimension.dimensionless()

    @Test
    fun testEquals() {
        println("Equals")

        assertTrue(length == length)
        assertTrue(length == Dimension.compose(length))
        assertFalse(length == Dimension())
        assertFalse(length == area)
        assertFalse(volume == length)
        assertTrue(area == area)
        assertTrue(area == Dimension.compose(length, length))
        assertFalse(area == volume)
        assertTrue(dimensionless == Dimension.dimensionless())
        assertFalse(dimensionless == length)
    }

    @Test
    fun testTimes() {
        println("Times")

        assertThat(length * dimensionless, `is`(length))
        assertThat(length * length, `is`(area))
        assertThat(length * area, `is`(volume))
    }

    @Test
    fun testDiv() {
        println("Div")

        assertThat(volume / dimensionless, `is`(volume))
        assertThat(volume / length, `is`(area))
        assertThat(volume / area, `is`(length))
        assertThat(volume / volume, `is`(dimensionless))
    }

    @Test
    fun testPow() {
        println("Pow")

        val time = Dimension()

        assertThat(time pow -2, `is`(dimensionless / time / time))
        assertThat(time pow -1, `is`(dimensionless / time))
        assertThat(time pow 0, `is`(dimensionless))
        assertThat(time pow 1, `is`(time))
        assertThat(time pow 2, `is`(time * time))
    }
}
