package com.tibibo.example

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AppKtTest {

    @Test
    fun incReturnValueTest() {
        val map = HashMap<String, Int>()
        assertEquals(map.inc("A", "B"), "B")
    }

    @Test
    fun incCountEqual1Test() {
        val map = hashMapOf<String, Int>()
        map.inc("A", "B")
        assertEquals(map["A B"], 1)
    }

    @Test
    fun incCountEqual2Test() {
        val map = hashMapOf("A B" to 1)
        map.inc("A", "B")
        assertEquals(map["A B"], 2)
    }

    @Test
    fun parseLineNoPreviousTest() {
        "A B".parseLine("") { last, current ->
            assertEquals(last, "A")
            assertEquals(current, "B")
            current
        }
    }

    @Test
    fun parseLinePreviousTest() {
        "B".parseLine("A") { last, current ->
            assertEquals(last, "A")
            assertEquals(current, "B")
            current
        }
    }

    @Test
    fun parseLineMultiDelimitersTest() {
        "A —!,. B".parseLine("") { last, current ->
            assertEquals(last, "A")
            assertEquals(current, "B")
            current
        }
    }

    @Test
    fun parseTest() {
        val app = Appp()
        app.parse("test1.txt")
        assertEquals(app.index, hashMapOf("a b" to 3, "b a" to 3, "a a" to 4, "b b" to 5))
    }
}