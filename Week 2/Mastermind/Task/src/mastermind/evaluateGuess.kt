package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rp = 0
    var wp = 0

    for (i in 0 until secret.length) {
        rp += if (secret[i] == guess[i]) 1 else 0
    }
    if (rp != secret.length) {
        var newSecret = ""
        var newGuess = ""
        for (i in 0 until secret.length) {
            if (secret[i] != guess[i]) {
                newSecret += secret[i]
                newGuess += guess[i]
            }
        }
        for (i in 0 until newSecret.length) {
            for (j in 0 until newGuess.length) {
                if (newSecret[i] == newGuess[j]) {
                    wp++
                    newGuess = newGuess.substring(0, j) + if (j <= newGuess.length - 2) newGuess.substring(j + 1) else ""
                    break
                }
            }
        }
    }

    return Evaluation(rp, wp)
}
