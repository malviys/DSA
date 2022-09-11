# Segment Tree

- Segment tree is a tree like data structure that is used to store the global or commutative value of a segment of
  subsequence/array

- Segment tree breaks down the subsequence/array in smaller parts and store the global value in their parent nodes

- For example if we want to store the max (global) for an array segment tree will break down the array in subsequent
  sections and store the max of each section in it's parent nodes, and a global max will be stored at root of tree.

- All the statements that required to find global solution can use segment tree

# Operations in Segment Trees

1. Build O(N)
2. updation O(logN)
3. query O(logN)

# Build

code

```kotlin
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
```

# Updation

Updation operation requires source array to get updated which will be constant operation and rebuilding part of tree
which will be LogN.

therefore the cost of updation will be 1 + LogN.

code

```kotlin
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
```

# Query

A query is performed onto the segment tree which return the global or commutative value over any given range in query

If we need to find the maximum value or sum of values over given range, query will return that desired value

E.G:

array = [1,2,3,4,5,6,7,8,9]
query = sum over [2,5]
result = [3+4+5+6] = 18

code

```kotlin
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
```
