package rationals

import java.math.BigInteger


class Rational(private var n : BigInteger, private var d : BigInteger) : Comparable<Rational> {

    init {
        if (d == BigInteger.ZERO) {
            throw IllegalArgumentException()
        }
        val pair = this.toNormalized()
        n = pair.first
        d = pair.second
    }

    private fun toNormalized() : Pair<BigInteger, BigInteger> {
        val gcd = n.gcd(d)
        if (d < 0.toBigInteger()) {
            n *= (-1).toBigInteger()
            d *= (-1).toBigInteger()
        }
        return n / gcd to d / gcd
    }

    override fun toString() : String = if (d == BigInteger.ONE) "$n" else "$n/$d"

    operator fun plus(other : Rational) : Rational = Rational(this.n * other.d + other.n * this.d, this.d * other.d)
    operator fun minus(other : Rational) : Rational = Rational(this.n * other.d - other.n * this.d, this.d * other.d)

    operator fun times(other : Rational) = Rational(this.n * other.n, this.d * other.d)
    operator fun div(other : Rational) = Rational(this.n * other.d, this.d * other.n)
    operator fun unaryMinus() = Rational(-this.n, this.d)

    override fun compareTo(other : Rational) : Int = (this.n * other.d).compareTo(this.d * other.n)

    override fun equals(other : Any?) : Boolean {
        if (other == null || this.javaClass != other.javaClass) return false
        return (this === other || this.compareTo(other as Rational) == 0)
    }
}

infix fun Int.divBy(d : Int) = Rational(this.toBigInteger(), d.toBigInteger())
infix fun Long.divBy(d : Long) = Rational(this.toBigInteger(), d.toBigInteger())
infix fun BigInteger.divBy(d : BigInteger) = Rational(this, d)


fun String.toRational() : Rational {
    return if (this.contains('/', true)) {
        Rational(this.substringBeforeLast("/").toBigInteger(), this.substringAfter("/").toBigInteger())
    } else {
        Rational(this.toBigInteger(), 1.toBigInteger())
    }

}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}