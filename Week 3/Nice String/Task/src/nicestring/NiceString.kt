package nicestring

fun String.isNice(): Boolean {
    if (this.length >= 2) {
        var cond = 0

        if (!this.contains("bu") && !this.contains("ba") && !this.contains("be")) cond++

        for (i in 1 until this.length) {
            if (this[i - 1] == this[i]) {
                cond++
                break
            }
        }

        if (this.length >= 3 && this.filter { c ->  c in setOf('a', 'e', 'i', 'o', 'u')}.count() >= 3) cond++

        return cond >= 2
    } else {
        return false
    }
}