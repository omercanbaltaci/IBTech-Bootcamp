import java.lang.NumberFormatException

fun main() {
    val list = mutableListOf<Any>("Hello", 8.4, 9, 2, "World", 3.5f, 5)

    print("Your list: ")
    print(list)
    println()

    var flag = true
    do {
        try {
            print("Enter an index you want to reverse from (Enter to skip, type -1 to exit.): ")
            val enteredIndex: String? = readLine()
            if (enteredIndex != null){
                if (enteredIndex == "") list.reverseByIndex()               // if index = Enter
                else if (enteredIndex.toInt() == -1) flag = false           // exit
                else if (enteredIndex.toInt() !in 0..list.size)             // if index is out of bounds
                    println("Index is out of bounds. Try again. (0-${list.size})")
                else list.reverseByIndex(enteredIndex.toInt())
            }
        } catch (e: NumberFormatException) {
            println("Wrong type of input has been entered. Try again.")
        }
    } while (flag)
}

/**
 * First off, we check if the given index is valid, then proceed to reverse the collection
 * starting with that given index by simply swapping elements.
 */
fun MutableList<Any>.reverseByIndex(index: Int = 0) {
    var noOfSwaps: Int = (this.size - index) / 2
    // first indicates the index of the first element to swap, same with second
    var first: Int = index
    var second: Int = this.size - 1
    while (noOfSwaps > 0) {
        // swapping operation
        val temp: Any = this.elementAt(first)
        this[first] = this.elementAt(second)
        this[second] = temp
        first++
        second--
        noOfSwaps--
    }

    print("Reversed list: ")
    println(this)
}