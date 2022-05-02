package canvas

import exceptions.CanvasException

class Canvas(dimensions: CanvasDimensions) {

    private val canvas = linkedMapOf<Int, MutableList<Char>>()

    init {
        validateInputDimensions(dimensions.width, dimensions.height)
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

    @Override
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        buildHorizontalBar(stringBuilder)
        this.canvas.forEach { row ->
            stringBuilder.append("#")
            row.value.forEach { column ->
                stringBuilder.append(column)
            }
            stringBuilder.append("#")
            stringBuilder.append("\n")
        }
        buildHorizontalBar(stringBuilder)
        return stringBuilder.toString()
    }

    private fun buildHorizontalBar(stringBuilder: StringBuilder) {
        for (i in 0..getWidth() + 1) {
            stringBuilder.append("#")
        }
        stringBuilder.append("\n")
    }

    private fun validateInputDimensions(width: Int, height: Int) {
        if (width <= 0 || height <= 0) throw CanvasException("Canvas cannot be created; negative or 0 values detected")
        if (width > maxWidth || height > maxHeight)
            throw CanvasException(
                "Canvas too large. Max width = $maxWidth, your width = $width, " +
                        "max height = $maxHeight, your height = $height"
            )
    }

    private fun validateCoordinates(point: Point) {
        when {
            point.xCoord > getWidth() -> throw CanvasException("x coordinate too large")
            point.xCoord < 0 -> throw CanvasException("x coordinate too small")
            point.yCoord > getHeight() -> throw CanvasException("y coordinate too large")
            point.yCoord < 0 -> throw CanvasException("y coordinate too small")
        }
    }

    companion object {
        const val maxWidth = 500
        const val maxHeight = 500
    }

    data class CanvasDimensions(val width: Int, val height: Int)
    data class BucketFillParameters(val point: Point, val colour: Char)
    data class Point(val xCoord: Int, val yCoord: Int)

}