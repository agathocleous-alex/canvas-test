package canvas

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RendererTest {

    @Test
    fun `draw horizontal line results in points on a single row within the range specified have changed to X`() {
        val canvas = Canvas(Canvas.CanvasDimensions(20, 20))
        val renderer = Renderer(canvas)
        val start = Canvas.Point(1, 5)
        val end = Canvas.Point(5, 5)
        renderer.drawShape(Pair(start, end))
        for(i in start.xCoord ..end.xCoord) {
            canvas.getPointColour(Canvas.Point(i, 5)) shouldBe 'X'
        }
    }

    @Test
    fun `draw vertical line results in points of a specific index across all the rows have changed to X`() {
        val canvas = Canvas(Canvas.CanvasDimensions(20, 20))
        val renderer = Renderer(canvas)
        val start = Canvas.Point(6, 4)
        val end = Canvas.Point(6, 14)
        renderer.drawShape(Pair(start, end))
        for(i in start.yCoord ..end.yCoord) {
            canvas.getPointColour(Canvas.Point(6, i)) shouldBe 'X'
        }
    }

    @Test
    fun `drawing a rectangle only changes the pixels needed to create the rectangle`() {
        val canvas = Canvas(Canvas.CanvasDimensions(20, 20))
        val renderer = Renderer(canvas)
        val start = Canvas.Point(4, 4)
        val end = Canvas.Point(16, 16)
        renderer.drawShape(Pair(start, end))
        for(i in start.yCoord ..end.yCoord) {
            canvas.getPointColour(Canvas.Point(4, i)) shouldBe 'X'
            canvas.getPointColour(Canvas.Point(16, i)) shouldBe 'X'
        }
        for(i in start.xCoord ..end.xCoord) {
            canvas.getPointColour(Canvas.Point(i, 4)) shouldBe 'X'
            canvas.getPointColour(Canvas.Point(i, 16)) shouldBe 'X'
        }
    }

    @Test
    fun `filling an area won't bleed over into another area`() {
        val canvas = Canvas(Canvas.CanvasDimensions(50, 50))
        val renderer = Renderer(canvas)
        val rectangleStart = Canvas.Point(10, 10)
        val rectangleEnd = Canvas.Point(20, 20)
        renderer.drawShape(Pair(rectangleStart, rectangleEnd))
        val fillParameters = Canvas.BucketFillParameters(Canvas.Point(15, 15), 'o')
        renderer.fill(fillParameters)
        canvas.getPointColour(Canvas.Point(21, 21)) shouldBe ' '
        canvas.getPointColour(Canvas.Point(15, 15)) shouldBe 'o'
    }

    @Test
    fun `filling a line changes every connected pixel that is the same as that line` () {
        val canvas = Canvas(Canvas.CanvasDimensions(50, 50))
        val renderer = Renderer(canvas)
        val rectangleStart = Canvas.Point(10, 10)
        val rectangleEnd = Canvas.Point(20, 20)
        renderer.drawShape(Pair(rectangleStart, rectangleEnd))
        val fillParameters = Canvas.BucketFillParameters(Canvas.Point(10, 10), 'o')
        renderer.fill(fillParameters)
        canvas.getPointColour(Canvas.Point(10, 10)) shouldBe 'o'
        canvas.getPointColour(Canvas.Point(11, 11)) shouldBe ' '
        canvas.getPointColour(Canvas.Point(20, 20)) shouldBe 'o'
        canvas.getPointColour(Canvas.Point(21, 21)) shouldBe ' '
    }
}