package instructions

import domain.BucketFillParameters
import domain.Canvas
import domain.CanvasDimensions
import domain.Point
import exceptions.NoCanvasException
import io.Renderer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.contrib.java.lang.system.ExpectedSystemExit
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


class InstructionTest {

    @Test
    fun `attempting to draw anything without a canvas results in a NoCanvasException`() {
        shouldThrow<NoCanvasException> { Line(Pair(Point(2, 2), Point(4, 4)))
            .executeInstruction(null)
        }
        shouldThrow<NoCanvasException> { Rectangle(Pair(Point(2, 2), Point(4, 4)))
            .executeInstruction(null)
        }
        shouldThrow<NoCanvasException> { BucketFill(BucketFillParameters(Point(2, 2), 'o'))
            .executeInstruction(null)
        }
    }

    @Test
    fun `draw horizontal line results in points on a single row within the range specified have changed to X`() {
        val canvas = Create(CanvasDimensions(20, 20)).executeInstruction(null)
        val start = Point(1, 5)
        val end = Point(5, 5)
        val linedCanvas = Line(Pair(start, end)).executeInstruction(canvas)
        Renderer.drawCanvas(canvas)
        Renderer.drawCanvas(linedCanvas)
        for(i in start.xCoord ..end.xCoord) {
            linedCanvas.getPointColour(Point(i, 5)) shouldBe 'X'
        }
    }

    @Test
    fun `draw vertical line results in points of a specific index across all the rows have changed to X`() {
        val canvas = Create(CanvasDimensions(20, 20)).executeInstruction(null)
        val start = Point(6, 4)
        val end = Point(6, 14)
        val line = Line(Pair(start, end))
        line.executeInstruction(canvas)
        for(i in start.yCoord ..end.yCoord) {
            canvas.getPointColour(Point(6, i)) shouldBe 'X'
        }
    }

    @Test
    fun `drawing a rectangle only changes the pixels needed to create the rectangle`() {
        val canvas = Create(CanvasDimensions(20, 20)).executeInstruction(null)
        val start = Point(4, 4)
        val end = Point(16, 16)
        val rectangle = Rectangle(Pair(start, end))
        rectangle.executeInstruction(canvas)
        for(i in start.yCoord ..end.yCoord) {
            canvas.getPointColour(Point(4, i)) shouldBe 'X'
            canvas.getPointColour(Point(16, i)) shouldBe 'X'
        }
        for(i in start.xCoord ..end.xCoord) {
            canvas.getPointColour(Point(i, 4)) shouldBe 'X'
            canvas.getPointColour(Point(i, 16)) shouldBe 'X'
        }
    }

    @Test
    fun `filling an area won't bleed over into another area`() {
        val canvas = Create(CanvasDimensions(50, 50)).executeInstruction(null)
        val rectangleStart = Point(10, 10)
        val rectangleEnd = Point(20, 20)
        Rectangle(Pair(rectangleStart, rectangleEnd)).executeInstruction(canvas)
        val fillParameters = BucketFillParameters(Point(15, 15), 'o')
        BucketFill(fillParameters).executeInstruction(canvas)
        canvas.getPointColour(Point(21, 21)) shouldBe ' '
        canvas.getPointColour(Point(17, 17)) shouldBe 'o'
    }

    @Test
    fun `filling the maximum sized canvas area will not throw a StackOverflowError`() {
        val canvas = Create(CanvasDimensions(500, 500)).executeInstruction(null)
        val fillParameters = BucketFillParameters(Point(250, 250), 'o')
        BucketFill(fillParameters).executeInstruction(canvas)
        val point = Point(35, 35)
        canvas.getPointColour(point) shouldBe 'o'
    }

    @Rule
    val exit: ExpectedSystemExit  = ExpectedSystemExit.none()

    @Test
    @Disabled
    fun `executing a quit instruction should quit the application`() {
        exit.expectSystemExitWithStatus(0)
        Quit(0).executeInstruction(Canvas(CanvasDimensions(1, 1)))
    }
}