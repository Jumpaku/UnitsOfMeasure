package jumpaku.unitsofmeasure


internal fun <K> simplifyMap(m: Map<K, Int>): Map<K, Int> = m.filter { (_, v) -> v != 0 }

internal fun <K> mergeMap(m0: Map<K, Int>, m1: Map<K, Int>): Map<K, Int> = (m0.toList() + m1.toList())
        .groupBy { it.first }
        .mapValues { (_, v) -> v.map { it.second }.sum() }
        .run { simplifyMap(toMap()) }

internal fun <K> multiplyMap(m: Map<K, Int>, i: Int): Map<K, Int> = simplifyMap(m.mapValues { (_, index) -> i*index })