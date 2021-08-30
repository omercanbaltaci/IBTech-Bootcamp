import java.lang.NumberFormatException

/**
 * Author: Ömer Can Baltacı
 * Date: 30/08/2021
 */

fun main() {
    val list = mutableListOf<Any>("Hello", 8.4, 9, 2, "World", 3.5f, 5)

    print("Your list: ")
    print(list)
    println()

    try {
        print("Enter an index you want to reverse from (Press Enter to skip): ")
        when (val enteredIndex: String? = readLine()) {
            "" -> list.reverseByIndex()                                  // meaning if the Enter key is pressed
            is String -> list.reverseByIndex(enteredIndex.toInt())       // the entered index will be converted to int
        }
    } catch (e: NumberFormatException) {
        print("Wrong type of input has been entered.")
    }
}

/**
 * First off, we check if the given index is valid, then proceed to reverse the collection
 * starting with that given index by simply swapping elements.
 */
fun MutableList<Any>.reverseByIndex(index: Int = 0) {
    // index check
    if (index < 0 || index >= this.size) {
        print("Wrong input.")
        return
    }

    var noOfReverses: Int = (this.size - index) / 2
    // first indicates the index of the first element to swap, same with second
    var first: Int = index
    var second: Int = this.size - 1
    while (noOfReverses > 0) {
        // swapping operation
        val temp: Any = this.elementAt(first)
        this[first] = this.elementAt(second)
        this[second] = temp
        first++
        second--
        noOfReverses--
    }

    print("Reversed list: ")
    print(this)
}