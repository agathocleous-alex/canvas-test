package instructions

import domain.BucketFillParameters
import domain.CanvasDimensions
import domain.Point
import exceptions.ExceptionBuilder

class InstructionFactory {

  companion object {

    private val canvasDimensionRegex = Regex("^[Cc]([\\s][\\d]+){2}$")
    private val lineRegex = Regex("^[Ll]([\\s][\\d]+){4}$")
    private val rectangleRegex = Regex("^[Rr]([\\s][\\d]+){4}$")
    private val quitRegex = Regex("^[Qq]$")
    private val bucketFillRegex = Regex("^[Bb]([\\s][\\d]+){2}[\\s][a-zA-Z\\d]\$")
    private const val offset = 1

    fun buildInstruction(input: String) : Instruction {
      return when {
        input.matches(canvasDimensionRegex) -> buildCreateInstruction(removeCommandSection(input))
        input.matches(lineRegex) -> buildLineInstruction(removeCommandSection(input))
        input.matches(bucketFillRegex) -> buildFillInstruction(removeCommandSection(input))
        input.matches(quitRegex) -> buildQuitInstruction()
        input.matches(rectangleRegex) -> buildRectangleInstruction(removeCommandSection(input))
        else -> { throw ExceptionBuilder.getInvalidInputException("Unrecognised Command") }
      }
    }

    private fun buildRectangleInstruction(params: String) : Rectangle {
      val raw = convertToIntArray(params).map { it - offset }
      return Rectangle(ensureLeftToRightOrder(Pair(Point(raw[0], raw[1]), Point(raw[2], raw[3]))))
    }

    private fun buildQuitInstruction() : Quit {
      return Quit(0)
    }

    private fun buildFillInstruction(params: String) : BucketFill  {
      val raw = params.trim().split(" ")
      return BucketFill(BucketFillParameters(
        point = Point(raw[0].toInt() - offset, raw[1].toInt() - offset),
        colour = raw[2].toCharArray().first()))
    }

    private fun buildLineInstruction(params: String) : Line {
      val raw = convertToIntArray(params).map { it - offset }
      return Line(ensureLeftToRightOrder(Pair(Point(raw[0], raw[1]), Point(raw[2], raw[3]))))
    }

    private fun buildCreateInstruction(params: String) : Create {
      val raw = convertToIntArray(params)
      return Create(CanvasDimensions(raw[0], raw[1]))
    }

    private fun convertToIntArray(parameterString: String) =
      parameterString
        .trim()
        .split(" ")
        .map { it.toInt() }

    private fun ensureLeftToRightOrder(pair: Pair<Point, Point>) : Pair<Point, Point> {
      if(pair.first.xCoord > pair.second.xCoord) {
        return Pair(pair.second, pair.first)
      }
      return pair
    }

    private fun removeCommandSection(parameterString: String) : String {
      if (parameterString.length <= 1) {
        return parameterString
      }
      return parameterString.removeRange(0, 1).trim()
    }

  }



}