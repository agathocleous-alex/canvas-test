package domain

import exceptions.ExceptionBuilder

class Canvas(dimensions: CanvasDimensions) {

    private val canvas = linkedMapOf<Int, MutableList<Char>>()

    init {
        for (i in 0 until dimensions.height) {
            canvas[i] = MutableList(size = dimensions.width) { ' ' }
        }
    }

    fun getWidth() = canvas[0]!!.size
    fun getHeight() = canvas.size

    fun getPointColour(point: Point) : Char {
        validateCoordinates(point)
        return canvas[point.yCoord]!![point.xCoord]
    }

    fun changePointColour(point: Point, colour: Char) {
        validateCoordinates(point)
        canvas[point.yCoord]!![point.xCoord] = colour
    }

    fun getMatrix() : LinkedHashMap<Int, MutableList<Char>> {
        return this.canvas
    }

    private fun validateCoordinates(point: Point) {
        when {
            point.xCoord >= getWidth() -> throw ExceptionBuilder.getCanvasException("x coordinate too large")
            point.xCoord < 0 -> throw ExceptionBuilder.getCanvasException("x coordinate too small")
            point.yCoord >= getHeight() -> throw ExceptionBuilder.getCanvasException("y coordinate too large")
            point.yCoord < 0 -> throw ExceptionBuilder.getCanvasException("y coordinate too small")
        }
    }
}