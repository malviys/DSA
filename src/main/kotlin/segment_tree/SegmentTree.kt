package segment_tree

val SUM = fun(a: Int, b: Int) = a.plus(b)
val MAX = fun(a: Int, b: Int) = a.coerceAtLeast(b)
val MIN = fun(a: Int, b: Int) = a.coerceAtMost(b)

class SegmentTree(private val array: Array<Int>, private val operation: (Int, Int) -> Int) {
    private val tree = IntArray(4 * array.size)

    init {
        build()
    }

    fun query(left: Int, right: Int) = query(qLeft = left, qRight = right)

    fun update(index: Int, value: Int) {
        if (index < 0 || index >= array.size)
            return

        array[index] = value
        update(idx = index)
    }

    private fun build(root: Int = 0, left: Int = 0, right: Int = array.size) {
        if (left >= array.size || right < 0)
            return

        if (left == right) {
            tree[root] = array[left]
            return
        }

        val mid = left + ((right - left) / 2)

        build(2 * root + 1, left, mid)
        build(2 * root + 2, mid + 1, right)

        tree[root] = operation(tree[2 * root + 1], tree[2 * root + 2])
    }

    private fun update(root: Int = 0, left: Int = 0, right: Int = array.size, idx: Int) {
        if (left == right) {
            tree[root] = array[left]
            return
        }

        val mid = left + (right - left) / 2

        if (idx <= mid) {
            update(2 * root + 1, left, mid, idx)
        }

        if (idx > mid) {
            update(2 * root + 2, mid + 1, right, idx)
        }

        tree[root] = operation(tree[2 * root + 1], tree[2 * root + 2])
    }

    private fun query(root: Int = 0, aLeft: Int = 0, aRight: Int = array.size, qLeft: Int, qRight: Int): Int {
        if (aRight < qLeft || aLeft > qRight)
            return 0

        if (aLeft >= qLeft && aRight <= qRight)
            return tree[root]

        val mid = aLeft + (aRight - aLeft) / 2

        return operation(
            query(2 * root + 1, aLeft, mid, qLeft, qRight),
            query(
                2 * root + 2, mid + 1, aRight, qLeft, qRight
            )
        )
    }
}

fun main(args: Array<String>) {
    val operation = MAX

    val segmentTree = SegmentTree(arrayOf(1, 2, 3, 4, 5, 6), operation)

    segmentTree.update(index = 0, value = 7)
    println("${operation.javaClass::getName} ${segmentTree.query(1, 3)}")
}

