package com.example.coursework8405
import kotlin.math.absoluteValue
import kotlin.math.sign

class CoordinateVH(
        degrees: Int = 0,
        minutes: UInt = 0u,
        seconds: UInt = 0u,
        direction: Direction = Direction.longitude
) {
    val direction = if (direction === Direction.longitude || direction === Direction.latitude) direction
        else error("Invalid direction value: $direction")
    val degrees = if (
            (degrees in -90..90 && direction === Direction.latitude) ||
            (degrees in -180..180 && direction === Direction.longitude)
    ) degrees else error("Degrees out of range: $degrees.\n Allowed values are between -180 and 180 for longitude and between -90 and 90 for latitude")
    val minutes = if (minutes in 0u..59u && ((degrees !== 90 && degrees !== -90 && degrees !== 180  && degrees !== -180) || minutes == 0u)) minutes
        else error("Minutes out of range: $minutes.\n Allowed values are between 0 and 59 for up to 90 degrees")
    val seconds = if (seconds in 0u..59u && ((degrees !== 90 && degrees !== -90 && degrees !== 180  && degrees !== -180) || seconds == 0u)) seconds
        else error("Seconds out of range: $seconds.\n Allowed values are between 0 and 59 for up to 90 degrees")

    val orientation = if (degrees <= 0 && direction == Direction.latitude) 'S'
        else if (direction == Direction.latitude) 'N'
        else if (degrees <= 0) 'W'
        else 'E'

    fun stringify() : String {
        return "${degrees.absoluteValue}°$minutes'$seconds\"$orientation\""
    }

    fun decimalStringify() : String {
        return "${((degrees.absoluteValue * 3600 + minutes.toInt() * 60 + seconds.toInt())).toFloat() / 3600}° $orientation)"
    }

    private fun signedSeconds() : Int {
        return (degrees.absoluteValue * 3600 + minutes.toInt() * 60 + seconds.toInt()) * degrees.sign
    }

    fun average(coordinate: CoordinateVH) : CoordinateVH? {
        if (direction !== coordinate.direction) return null
        val baseSeconds = signedSeconds()
        val incomingSeconds = coordinate.signedSeconds()

        val averageSeconds = ((baseSeconds + incomingSeconds) / 2)

        val newDegrees = (averageSeconds / 3600)
        val leftover = averageSeconds % 3600
        val newMinutes = (leftover / 60).toUInt()
        val newSeconds = (leftover % 60).toUInt()
        return CoordinateVH(newDegrees, newMinutes, newSeconds, direction)
    }

    companion object {
        fun average(coordinate1 : CoordinateVH, coordinate2 : CoordinateVH) : CoordinateVH? {
            return coordinate1.average(coordinate2)
        }
    }
}

enum class Direction {
    latitude,
    longitude
}

fun main() {
    val coordinate1 = CoordinateVH()
    println("Zero-value initialization")
    println(coordinate1.stringify())

    val coordinate2 = CoordinateVH(34,14u,56u, Direction.latitude)
    println("Latitude coordinate initialization")
    println(coordinate2.stringify())

    val coordinate3 = CoordinateVH(120, 21u, 33u, Direction.longitude)
    println("Longitude coordinate initialization")
    println(coordinate3.stringify())

    val coordinate4 = CoordinateVH(-20, 42u, 11u, Direction.latitude)
    println("Represent coordinates using decimal stringify")
    println(coordinate4.decimalStringify())

    val avarageCoordinate = coordinate2.average(coordinate4)
    println("Avarage coordinate")
    println(avarageCoordinate?.stringify())

    //val coordinateTooBig = CoordinateVH(90, 15u, 9u, Direction.latitude)

    val coordinateNegative = CoordinateVH(-12, 30u, 40u, Direction.latitude)
    println(coordinateNegative.stringify())
    println(coordinateNegative.decimalStringify())


}




main()