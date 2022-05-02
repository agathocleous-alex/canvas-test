package canvas

class Renderer(private val canvas: Canvas) {

    //maybe add some kind of stack that holds the incoming instruction set?

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
            canvas.changePointColour(Canvas.Point(column, i), 'X')
        }
    }

    private fun drawHorizontalLine(row: Int, range: Pair<Int, Int>) {
        for(i in range.first..range.second) {
            canvas.changePointColour(Canvas.Point(i, row), 'X')
        }
    }

    fun fill(fillParameters: Canvas.BucketFillParameters) {
        val groundZeroColour = canvas.getPointColour(fillParameters.point)
        flood(fillParameters, groundZeroColour)
    }

    private fun flood(fillParameters: Canvas.BucketFillParameters, groundZeroColour: Char) {
        if(!inBounds(fillParameters.point)) return
        if(canvas.getPointColour(fillParameters.point) != groundZeroColour) return
        if(canvas.getPointColour(fillParameters.point) == fillParameters.colour) return
        canvas.changePointColour(fillParameters.point, fillParameters.colour)

        flood(Canvas.BucketFillParameters(Canvas.Point(fillParameters.point.xCoord + 1, fillParameters.point.yCoord), fillParameters.colour), groundZeroColour)
        flood(Canvas.BucketFillParameters(Canvas.Point(fillParameters.point.xCoord - 1, fillParameters.point.yCoord), fillParameters.colour), groundZeroColour)
        flood(Canvas.BucketFillParameters(Canvas.Point(fillParameters.point.xCoord, fillParameters.point.yCoord + 1), fillParameters.colour), groundZeroColour)
        flood(Canvas.BucketFillParameters(Canvas.Point(fillParameters.point.xCoord, fillParameters.point.yCoord - 1), fillParameters.colour), groundZeroColour)
    }

    private fun inBounds(point: Canvas.Point) =
        ((point.xCoord < canvas.getWidth()) and (point.xCoord >= 0)) and
        ((point.yCoord < canvas.getHeight()) and (point.yCoord >= 0))

    private fun isLine(points: Pair<Canvas.Point, Canvas.Point>) = isHorizontal(points) || isVertical(points)
    private fun isHorizontal(points: Pair<Canvas.Point, Canvas.Point>) = points.first.yCoord == points.second.yCoord
    private fun isVertical(points: Pair<Canvas.Point, Canvas.Point>) = points.first.xCoord == points.second.xCoord
}
