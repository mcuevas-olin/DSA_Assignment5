package org.example

class SquareMatrix<T:Number>(private val type: Class<T>, private val initialSize: Int) {
    private var size = initialSize
    private var matrix: Array<Array<Any?>> = Array(size) { Array(size) { getDefault() } }

    // Custom operator for setting a value at a specific row and column index
    operator fun set(row: Int, col: Int, value: T) {
        ensureCapacity(row, col)
        matrix[row][col] = value
    }

    // Custom operator for getting a value at a specific row and column index
    @Suppress("UNCHECKED_CAST")
    operator fun get(row: Int, col: Int): T {
        require(row in 0 until size && col in 0 until size) { "Invalid indices" }
        return matrix[row][col] as T
    }

    operator fun plus(other: SquareMatrix<T>) : SquareMatrix<T>{
        require(size == other.size)

        val resultData = SquareMatrix(type, size)

        for (i in 0 until size){
            for (j in 0 until size){
                ((this[i, j] + other[i, j]) as T).also { resultData[i, j] = it }
            }
        }
        return resultData
    }

    operator fun minus(other: SquareMatrix<T>) : SquareMatrix<T>{
        require(size == other.size)

        val resultData = SquareMatrix(type, size)

        for (i in 0 until size){
            for (j in 0 until size){
                (this[i, j] - other[i, j]).also {
                    difference<T> ->
                    resultData[i, j] = difference}
            }
        }
        return resultData
    }

    // Custom operator for printing the matrix
    operator fun invoke() {
        for (row in matrix) {
            for (element in row) {
                print("$element\t")
            }
            println()
        }
    }

    // Ensure capacity and resize if necessary
    private fun ensureCapacity(row: Int, col: Int) {
        if (row >= size || col >= size) {
            val newSize = maxOf(row + 1, col + 1, size * 2)
            resize(newSize)
        }
    }

    // Resize the matrix to the specified size
    fun resize(newSize: Int) {
        val newMatrix : Array<Array<Any?>> = Array(newSize) { Array(newSize) { getDefault() } }

        for (i in 0 until size) {
            for (j in 0 until size) {
                newMatrix[i][j] = matrix[i][j]
            }
        }

        matrix = newMatrix
        size = newSize
    }

    // Get the default value for the type
    private fun getDefault(): T {
        return when (type) {
            Int::class.java -> 0 as T
            Double::class.java -> 0.0 as T
            Float::class.java -> 0.0f as T
            Long::class.java -> 0L as T
            else -> throw IllegalArgumentException("Unsupported type: $type")
        }as? T ?: throw IllegalArgumentException("Invalid cast for type: $type")
    }

    /**
     * Multiply [this] matrix by [other].
     * You can implement this either using block-based matrix multiplication or
     * traditional matrix multiplication (the kind you learn about in math
     * classes!)
     * @return [this]*[other] if the dimensions are compatible and null otherwise
     */
    fun multiply(other: SquareMatrix<T>):SquareMatrix<T>?{
        //check dimensions for compatability
        if (matrix.size != other.size){
            //dimensions are not compatible so return null
            return null
        }
        val result = SquareMatrix(type, size)
        for (i in 0 until size){
            for (j in 0 until size){
                var sum : T = 0 as T
                for(k in 0 until size){
                    sum +=  get(i,k) * other[k, j]
                }
                result[i, j] = sum
            }
        }
        return result
    }

    private fun nextPowerOf2(number: Int): Int {
        var result = 1
        while (result < number) {
            result *= 2
        }
        return result
    }

    /**
     * Multiply [this] matrix by [other].
     * Your code should use Strassen's algorithm
     * @return [this]*[other] if the dimensions are compatible and null otherwise
     */
    fun strassenMultiply(other: Matrix):Matrix? {

        //find what size to pad to make matrixes powers of 2
        val newSize = nextPowerOf2(size)

        //prepare matrix A
        resize(newSize)

        //prepare matrix B
        other.resize()

        //prepare matrix C
        var C = SquareMatrix(type, initialSize)





    }
}


// Factory function to create SquareMatrix instances with reified type
inline fun <reified T : Number> createSquareMatrix(initialSize: Int): SquareMatrix<T> {
    return SquareMatrix(T::class.java, initialSize)
}



