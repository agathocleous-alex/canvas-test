package instructions

import domain.Canvas
import domain.Point
import exceptions.ExceptionBuilder

class Rectangle(private val points: Pair<Point, Point>) : Instruction() {
  override fun executeInstruction(canvas: Canvas?): Canvas {
    if(canvas == null) {
      throw ExceptionBuilder.getNoCanvasException()
    }
    val x1y1 = points.first
    val x1y2 = Point(points.first.xCoord, points.second.yCoord)
    val x2y1 = Point(points.second.xCoord, points.first.yCoord)
    val x2y2 = points.second
    drawVerticalLine(canvas, Pair(x1y1, x1y2))
    drawVerticalLine(canvas, Pair(x2y1, x2y2))
    drawHorizontalLine(canvas, Pair(x1y1, x2y1))
    drawHorizontalLine(canvas, Pair(x1y2, x2y2))
    return canvas
  }
}