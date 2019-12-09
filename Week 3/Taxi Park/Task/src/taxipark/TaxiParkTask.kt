package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filterNot { it in this.trips.map { it.driver } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    this.allPassengers.filter { p ->
        this.trips.flatMap { it.passengers }.count { it == p } >= minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter { p ->
            this.trips.filter{t -> t.driver == driver }.flatMap { it.passengers }.count { it == p } > 1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter { p ->
            this.trips.filter { it.discount != null }.flatMap { it.passengers }.count { it == p } >
            this.trips.filter { it.discount == null }.flatMap { it.passengers }.count { it == p }
        }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return if (this.trips.isEmpty())  {
        null
    } else {
        this.trips.map { IntRange(it.duration - it.duration % 10, it.duration - it.duration % 10 + 9) }
                .groupBy { it }.maxBy { s -> s.value.size }?.key
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (this.trips.isEmpty()) return false
    val cost80 = this.trips.map { it.cost }.sum() * 0.8
    val drivers20 = (this.allDrivers.size * 0.2).toInt()

    var xz =  this.trips.groupBy { it.driver }.map {it.value.map{ it.cost }.sum()}.sortedDescending()

    var sum = .0
    for (i in 0 until drivers20) {
        sum += xz[i]
    }
    return (sum >= cost80)
}