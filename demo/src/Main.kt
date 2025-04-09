import java.text.SimpleDateFormat
import java.util.*

fun sayHello (greeting: String, vararg itemsToGreet:String) {
    itemsToGreet.forEach { println("$greeting $it") }
}

fun testDate() {
    val ts = 0L
    val date = Date(ts)

    val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sdf2.timeZone = TimeZone.getTimeZone("America/New_York")

    println("Local date   == ${sdf1.format(date)}")
    println("NewYork date == ${sdf2.format(date)}")
}

fun main() {

//    val interestingThings = arrayOf<String>("Kotlin", "Programming", "Comic Books")
//    interestingThings.forEachIndexed {index, it ->
//        println("$it is at index $index")
//    }
//    val map = mutableMapOf<Int, String>(1 to "a", 2 to "b")
//    map.forEach { (key, value) -> println("$key -> $value")}


    // sayHello("Hi", *interestingThings)
//    testDate();


}

suspend fun pingAzure() {

}