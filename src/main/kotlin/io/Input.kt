package io

import java.util.Scanner

class Input {
    companion object {
        fun read() : String {
            val scanner = Scanner(System.`in`)
            if(scanner.hasNextLine()) {
                return scanner.nextLine()!!.trim().replace(Regex("[\\s]+"), " ")
            }
            scanner.close()
            return " "
        }
    }
}