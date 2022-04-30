package core.canvas

import canvas.Canvas
import exceptions.CanvasException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

class CanvasTest {

    @Test
    fun `creating a canvas` () {
        val canvas = Canvas(Canvas.CanvasDimensions(width = 2, height = 3))
        canvas.shouldBeTypeOf<Canvas>()
        canvas.getHeight() shouldBe 3
        canvas.getWidth() shouldBe 2
    }

    @Test
    fun `creating a canvas larger than max values should cause CanvasException` () {
        val canvas = shouldThrow<CanvasException> { Canvas(Canvas.CanvasDimensions(Canvas.maxWidth + 1, Canvas.maxHeight + 1)) }
        canvas.message shouldStartWith "Canvas too large."
    }

    @Test
    fun `creating a canvas with negative dimensions should cause CanvasException` () {
        val canvas = shouldThrow<CanvasException> { Canvas(Canvas.CanvasDimensions(-3, -1)) }
        canvas.message shouldStartWith "Canvas cannot be created"
    }

    @Test
    fun `return correct canvas dimensions` () {
        val canvas = Canvas(Canvas.CanvasDimensions(2, 3))
        canvas.getWidth() shouldBe 2
        canvas.getHeight() shouldBe 3
    }

    @Test
    fun `return value of valid coordinate` () {
        val canvas = Canvas(Canvas.CanvasDimensions(2, 3))
        val characterAtCoordinate = canvas.getPointColour(Canvas.Point(1, 1))
        characterAtCoordinate shouldBe ' '
    }

    @Test
    fun `getting an invalid coordinate should throw CanvasException`() {
        val canvas = Canvas(Canvas.CanvasDimensions(2, 3))
        shouldThrow<CanvasException> { canvas.getPointColour(Canvas.Point(1, 4)) }
            .message shouldBe "y coordinate too large"
        shouldThrow<CanvasException> { canvas.getPointColour(Canvas.Point(1, -4)) }
            .message shouldBe "y coordinate too small"
        shouldThrow<CanvasException> { canvas.getPointColour(Canvas.Point(3, 2)) }
            .message shouldBe "x coordinate too large"
        shouldThrow<CanvasException> { canvas.getPointColour(Canvas.Point(-3, 4)) }
            .message shouldBe "x coordinate too small"
    }

    @Test
    fun `change valid coordinate` () {
        val canvas = Canvas(Canvas.CanvasDimensions(5, 5))
        val point = Canvas.Point(1, 1)
        canvas.changePoint(point, 'O')
        canvas.getPointColour(point) shouldBe 'O'
    }
}