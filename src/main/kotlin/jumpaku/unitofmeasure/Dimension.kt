package jumpaku.unitofmeasure

class Dimension private constructor(val map: Map<Element, Int>) {

    constructor(): this(mapOf(Element() to 1))
    private class Element

    fun pow(i: Int): Dimension = Dimension(multiplyMap(map, i))

    operator fun times(d: Dimension): Dimension = Dimension(mergeMap(map, d.map))

    operator fun div(d: Dimension): Dimension = times(d.pow(-1))

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Dimension -> false
        else -> map == other.map
    }

    override fun hashCode(): Int = map.hashCode()

    companion object {

        val ONE: Dimension = Dimension(mapOf())
    }
}