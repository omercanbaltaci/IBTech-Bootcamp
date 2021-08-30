fun main() {
    val arr = mutableListOf<Any>("Hello", 8.4, 9, 2, "World", 3.5f, 5)

    print("Your list: ")
    print(arr)
    println()

    print("Enter an index you want to reverse from (Press enter to skip): ")
    when(val enteredIndex: String? = readLine()) {
        "" -> arr.reverseByIndex()
        is String -> arr.reverseByIndex(enteredIndex.toInt())
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
    var first: Int = index
    var second: Int = this.size - 1
    while (noOfReverses > 0) {
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