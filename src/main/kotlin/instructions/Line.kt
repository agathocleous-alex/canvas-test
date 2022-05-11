package instructions

import domain.Canvas
import domain.Point
import exceptions.ExceptionBuilder
import exceptions.InvalidInputException

class Line(private val points: Pair<Point, Point>) : Instruction() {

  override fun executeInstruction(canvas: Canvas?): Canvas {
    if(canvas == null) {
      throw ExceptionBuilder.getNoCanvasException()
    }

    if(isDiagonal(points)) {
      throw ExceptionBuilder.getInvalidInputException("Unfortunately, we don't yet support diagonal lines. Check back later!")
    }

    if(isVertical(points)) {
      drawVerticalLine(canvas, points)

    }
    if(isHorizontal(points)) {
      drawHorizontalLine(canvas, points)
    }

    return canvas
  }

  private fun isDiagonal(points: Pair<Point, Point>) = !isHorizontal(points) and !isVertical(points)
  private fun isHorizontal(points: Pair<Point, Point>) = points.first.yCoord == points.second.yCoord
  private fun isVertical(points: Pair<Point, Point>) = points.first.xCoord == points.second.xCoord
}