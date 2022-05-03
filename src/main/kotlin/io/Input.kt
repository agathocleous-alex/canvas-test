package io

import java.io.BufferedReader
import java.io.InputStreamReader

class Input {
    companion object {
        fun read() : String {
            return BufferedReader(InputStreamReader(System.`in`)).readLine()!!.trim().replace(Regex("[\\s]+"), " ")
        }
    }
}