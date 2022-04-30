package canvas

class Renderer(private val canvas: Canvas) {

    //maybe add some kind of stack

    fun drawShape(points: Pair<Canvas.Point, Canvas.Point>) {
        if(isLine(points)) {
            drawLine(points)
            return
        }
        drawRectangle(points)
    }

    private fun drawRectangle(points: Pair<Canvas.Point, Canvas.Point>) {
        drawVerticalLine(points.first.xCoord, Pair(points.first.yCoord, points.second.yCoord))
        drawVerticalLine(points.second.xCoord, Pair(points.first.yCoord, points.second.yCoord))
        drawHorizontalLine(points.first.yCoord, Pair(points.first.xCoord, points.second.xCoord))
        drawHorizontalLine(points.second.yCoord, Pair(points.first.xCoord, points.second.xCoord))
    }

    private fun drawLine(points: Pair<Canvas.Point, Canvas.Point>) {
        when {
            isVertical(points) ->
                drawVerticalLine(points.first.xCoord, Pair(points.first.yCoord, points.second.yCoord))
            isHorizontal(points) ->
                drawHorizontalLine(points.first.yCoord, Pair(points.first.xCoord, points.second.xCoord))
        }
    }

    private fun drawVerticalLine(column: Int, range: Pair<Int, Int>) {
        for(i in range.first .. range.second) {
            canvas.changePoint(Canvas.Point(column, i), 'X')
        }
    }

    private fun drawHorizontalLine(row: Int, range: Pair<Int, Int>) {
        for(i in range.first..range.second) {
            canvas.changePoint(Canvas.Point(i, row), 'X')
        }
    }

    fun fill() {

    }

    private fun isLine(points: Pair<Canvas.Point, Canvas.Point>) = isHorizontal(points) || isVertical(points)
    private fun isHorizontal(points: Pair<Canvas.Point, Canvas.Point>) = points.first.yCoord == points.second.yCoord
    private fun isVertical(points: Pair<Canvas.Point, Canvas.Point>) = points.first.xCoord == points.second.xCoord
}
