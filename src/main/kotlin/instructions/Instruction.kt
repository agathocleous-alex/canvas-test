package instructions

import domain.*

sealed class Instruction {

  abstract fun executeInstruction(canvas: Canvas?) : Canvas

  protected fun drawVerticalLine(canvas: Canvas, points: Pair<Point, Point>) {
    for(i in points.first.yCoord .. points.second.yCoord) {
      canvas.changePointColour(Point(points.first.xCoord, i), 'X')
    }
  }

  protected fun drawHorizontalLine(canvas: Canvas, points: Pair<Point, Point>) {
    for(i in points.first.xCoord..points.second.xCoord) {
      canvas.changePointColour(Point(i, points.first.yCoord), 'X')
    }
  }
}



