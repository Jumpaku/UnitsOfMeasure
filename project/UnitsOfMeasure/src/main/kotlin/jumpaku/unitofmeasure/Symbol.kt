package jumpaku.unitofmeasure

class Symbol private constructor(private val symbolMap: Map<Base, String>, private val structureMap: Map<Base, Int>) {

    private class Base

    val symbolString: String = structureMap.toList().run {
        when {
            isEmpty() -> ""
            else -> joinToString("*") { (s, i) -> symbolMap[s] + if (i == 1) "" else "^$i" }
        }
    }

    fun pow(i: Int): Symbol = multiplyMap(structureMap, i).let {
        Symbol(symbolMap.filterKeys { k -> k in it.keys }, it)
    }

    operator fun times(s: Symbol): Symbol  = mergeMap(structureMap, s.structureMap).let {
        Symbol((symbolMap + s.symbolMap).filterKeys { k -> k in it.keys }, it)
    }

    operator fun div(s: Symbol): Symbol = times(s.pow(-1))

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Symbol -> false
        else -> structureMap == other.structureMap
    }

    override fun hashCode(): Int = structureMap.hashCode()

    companion object {

        val empty: Symbol = Symbol(mapOf(), mapOf())

        operator fun invoke(symbolString: String): Symbol = Base().let { Symbol(mapOf(it to symbolString), mapOf(it to 1)) }
    }
}