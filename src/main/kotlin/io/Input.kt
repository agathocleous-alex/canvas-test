package io

import java.util.Scanner

class Input {
    companion object {
        fun read() : String {
            return with(Scanner(System.`in`))  { return@with nextLine() } as String
        }
    }
}