package io.parser

import canvas.Canvas
import exceptions.InvalidInputException

class Parser {
    companion object {
        private const val canvasDimensionRegex = "^C([\\s][\\d]+){2}$"
        private const val shapeRegex = "^[LR]([\\s][\\d]+){4}$"
        private const val bucketFillRegex = "^B([\\s][\\d]+){2}[\\s][a-zA-Z\\d]\$"

        fun parseCommand(inputString: String) : Command {
            return when {
                inputString.startsWith("C") -> Command.CREATE
                inputString.startsWith("L") -> Command.LINE
                inputString.startsWith("R") -> Command.RECTANGLE
                inputString.startsWith("B") -> Command.BUCKET_FILL
                inputString.startsWith("Q") -> Command.QUIT
                else -> throw InvalidInputException("Unrecognised Command")
            }
        }

        fun parseCanvasDimensions(input: String) : Canvas.CanvasDimensions {
            if(!input.matches(Regex(canvasDimensionRegex))) {
                throw InvalidInputException("Invalid input detected. | $input | is not valid input.")
            }
            val raw = parseToIntArray(removeCommandSection(input))
            return Canvas.CanvasDimensions(raw[0], raw[1])
        }

        fun parseShapeParameters(input: String) : Pair<Canvas.Point, Canvas.Point> {
            if(!input.matches(Regex(shapeRegex))) {
                throw InvalidInputException("Invalid input detected. | $input | is not valid input.")
            }
            val raw = parseToIntArray(removeCommandSection(input))
            val pair = Pair( Canvas.Point(raw[0], raw[1]), Canvas.Point(raw[2], raw[3]))
            return ensureLeftToRightOrder(pair)
        }

        fun parseBucketFillParameters(input: String) : Canvas.BucketFillParameters {
            if(!input.matches(Regex(bucketFillRegex))) {
                throw InvalidInputException("Invalid input detected. | $input | is not valid input.")
            }
            val raw = removeCommandSection(input).split(" ")
            return Canvas.BucketFillParameters(Canvas.Point(raw[0].toInt(), raw[1].toInt()), raw[2].first())
        }

        private fun ensureLeftToRightOrder(pair: Pair<Canvas.Point, Canvas.Point>) : Pair<Canvas.Point, Canvas.Point> {
            if(pair.first.xCoord > pair.second.xCoord) {
                return Pair(pair.second, pair.first)
            }
            return pair
        }
        private fun parseToIntArray(parameterString: String) = parameterString.split(" ").map { it.toInt() }
        private fun removeCommandSection(parameterString: String) = parameterString.substring(2)
    }

}