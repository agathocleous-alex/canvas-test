package canvas

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RendererTest {

    @Test
    fun `draw horizontal line`() {
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
    fun `draw vertical line`() {
        val canvas = Canvas(Canvas.CanvasDimensions(20, 20))
        val renderer = Renderer(canvas)
        val start = Canvas.Point(6, 4)
        val end = Canvas.Point(6, 14)
        renderer.drawShape(Pair(start, end))
        for(i in start.yCoord ..end.yCoord) {
            canvas.getPointColour(Canvas.Point(6, i)) shouldBe 'X'
        }
        println(canvas)
    }

    @Test
    fun `draw rectangle`() {
        val canvas = Canvas(Canvas.CanvasDimensions(20, 20))
        val renderer = Renderer(canvas)
        val start = Canvas.Point(4, 4)
        val end = Canvas.Point(16, 16)
        renderer.drawShape(Pair(start, end))
        println(canvas)
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
    fun `fill area`() {
        //it's going to be even more fun testing this....
    }
}