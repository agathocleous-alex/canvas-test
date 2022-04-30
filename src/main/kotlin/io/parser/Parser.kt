package io.parser

import canvas.Canvas
import exceptions.InvalidInputException
import java.util.regex.Pattern

class Parser {
    companion object {
        fun extractCommand(inputString: String) : Command {
            return when {
                inputString.startsWith("C") -> Command.CREATE
                inputString.startsWith("L") -> Command.LINE
                inputString.startsWith("R") -> Command.RECTANGLE
                inputString.startsWith("B") -> Command.BUCKET_FILL
                inputString.startsWith("Q") -> Command.QUIT
                else -> throw InvalidInputException("Unrecognised Command")
            }
        }

        fun parseCanvasDimensions(parameterString: String) : Canvas.CanvasDimensions {
            try {
                val raw = parseToIntArray(parameterString)
                if (raw.size != 2) {
                    throw InvalidInputException("Invalid number of parameters. Please provide width and height separated with a space")
                }
                return Canvas.CanvasDimensions(raw[0], raw[1])
            } catch (nfe: NumberFormatException) {
                throw InvalidInputException("Invalid parameter detected. Please input only two integers separated with a space")
            }
        }

        fun parseShapeParameters(parameterString: String) : Pair<Canvas.Point, Canvas.Point> {
            try {
                val raw = parseToIntArray(parameterString)
                if (raw.size != 4) {
                    throw InvalidInputException("Invalid number of parameters. Please provide in format x1 y1 x2 y2")
                }
                val pair = Pair( Canvas.Point(raw[0], raw[1]), Canvas.Point(raw[2], raw[3]))
//                validatePairNotDiagonal(pair)
                return ensureLeftToRightOrder(pair)
            } catch(nfe: NumberFormatException) {
                throw InvalidInputException("Invalid parameter detected. Please input four integers seperated by a space")
            }
        }

        fun parseBucketFillParameters(parameterString: String) : Canvas.BucketFillParameters {
            val raw = parameterString.split(" ").map { it.trim() }
            if(raw.size > 3) {
                throw InvalidInputException("Invalid number of parameters. Please provide in format x y c")
            }

            if(raw.last().length > 1) {
                throw InvalidInputException("Invalid colour; colour must be a single character")
            }
            try{
                return Canvas.BucketFillParameters(Canvas.Point(raw[0].toInt(), raw[1].toInt()), raw[2].first())
            } catch (nfe: NumberFormatException) {
                throw InvalidInputException("Invalid parameter detected. Please input two integers seperated by a space")
            }
        }

//        private fun validatePairNotDiagonal(pair: Pair<Canvas.Point, Canvas.Point>) {
//            if(pair.first.yCoord == pair.second.yCoord){
//                return
//            }
//            if(pair.first.xCoord == pair.second.xCoord) {
//                return
//            }
//            throw InvalidInputException("Entered line is diagonal. Currently only straight lines are supported")
//        }

        private fun ensureLeftToRightOrder(pair: Pair<Canvas.Point, Canvas.Point>) : Pair<Canvas.Point, Canvas.Point> {
            if(pair.first.xCoord > pair.second.xCoord) {
                return Pair(pair.second, pair.first)
            }
            return pair
        }

        private fun parseToIntArray(parameterString: String) = parameterString.split(" ").map { it.toInt() }

    }

}