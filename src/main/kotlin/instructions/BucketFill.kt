package instructions

import domain.BucketFillParameters
import domain.Canvas
import domain.Point
import exceptions.ExceptionBuilder

class BucketFill(private val fillParameters: BucketFillParameters) : Instruction() {

  override fun executeInstruction(canvas: Canvas?): Canvas {
    if(canvas == null) {
      throw ExceptionBuilder.getNoCanvasException()
    }
    val originColour = canvas.getPointColour(fillParameters.point)
    floodFill(canvas, fillParameters, originColour)
    return canvas
  }

  private fun floodFill(canvas: Canvas, fillParameters: BucketFillParameters, originColour: Char) {
    val queue = ArrayDeque<Point>()
    queue.add(fillParameters.point)
    canvas.changePointColour(fillParameters.point, fillParameters.colour)

    while (queue.size > 0){
      val currentPoint = queue.first()
      queue.removeFirst()

      val north = Point(currentPoint.xCoord, currentPoint.yCoord + 1)
      if(isValid(canvas, BucketFillParameters(north, fillParameters.colour), originColour)) {
        canvas.changePointColour(north, fillParameters.colour)
        queue.add(north)
      }
      val south = Point(currentPoint.xCoord, currentPoint.yCoord - 1)
      if(isValid(canvas, BucketFillParameters(south, fillParameters.colour), originColour)) {
        canvas.changePointColour(south, fillParameters.colour)
        queue.add(south)
      }
      val east = Point(currentPoint.xCoord + 1, currentPoint.yCoord)
      if(isValid(canvas, BucketFillParameters(east, fillParameters.colour), originColour)) {
        canvas.changePointColour(east, fillParameters.colour)
        queue.add(east)
      }
      val west = Point(currentPoint.xCoord - 1, currentPoint.yCoord)
      if(isValid(canvas, BucketFillParameters(west, fillParameters.colour), originColour)) {
        canvas.changePointColour(west, fillParameters.colour)
        queue.add(west)
      }
    }
  }

  private fun isValid(canvas: Canvas, bucketFillParameters: BucketFillParameters, originColour: Char) =
    isWithinBounds(bucketFillParameters.point, canvas) && isValidColour(canvas, bucketFillParameters, originColour)

  private fun isWithinBounds(point: Point, canvas: Canvas) =
    isWidthValid(point, canvas.getWidth()) && isHeightValid(point, canvas.getHeight())

  private fun isHeightValid(point: Point, maxHeight: Int) = ((point.yCoord >= 0) && (point.yCoord <= maxHeight - 1))
  private fun isWidthValid(point: Point, maxWidth: Int) = ((point.xCoord >= 0) && (point.xCoord <= maxWidth - 1))
  private fun isValidColour(canvas: Canvas, fillParameters: BucketFillParameters, originColour: Char,) =
    (canvas.getPointColour(fillParameters.point) == originColour) && (canvas.getPointColour(fillParameters.point) != fillParameters.colour)
}