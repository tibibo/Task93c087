package com.tibibo.example

import java.io.File
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

val delimiters = arrayOf(" ", ",", ".", "!", "-", "—", "?", "(", ")", "\"")

fun HashMap<String, Int>.inc(last: String, current: String): String {
    this["$last $current"] = (this["$last $current"] ?: 0) + 1
    return current
}

fun String.parseLine(previous: String?, body: (String, String) -> String): String {
    return "$previous $this"
        .split(*delimiters) // "-" could be as a part of a word, but we take this case as two words.
        .filter { !it.isBlank() }
        .reduce(body)
}

class Appp {

    internal val index = hashMapOf<String, Int>()

    fun run(fileName: String) {
        val file = File(fileName)
        if (file.exists()) {
            val time = measureTimeMillis {
                parse(fileName)
                output("${file.name}-output.txt")
            }

            println("Parsing run in $time ms. Result in ${file.name}-output.txt file.")
        } else {
            println("File with given name doesn't exist")
        }
    }

    internal fun parse(file: String) {

        File(file).useLines { lines ->
            lines.reduce { line, previous ->
                line.parseLine(previous) { last, current ->
                    index.inc(last.toLowerCase(), current.toLowerCase())
                }
            }
        }
    }

    internal fun output(file: String) {
        File(file).printWriter().use { out ->
            index.map {
                out.println(it.key + " = " + it.value)
            }
        }
    }
}


fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Please provide a text file name as a command-line argument")
        return
    }

    Appp().run(args[0])
}

