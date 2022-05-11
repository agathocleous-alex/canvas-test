package domain

import exceptions.CanvasException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

class CanvasTest {

    @Test
    fun `creating a canvas` () {
        val canvas = Canvas(CanvasDimensions(width = 2, height = 3))
        canvas.shouldBeTypeOf<Canvas>()
        canvas.getHeight() shouldBe 3
        canvas.getWidth() shouldBe 2
    }

    @Test
    fun `return value of valid coordinate` () {
        val canvas = Canvas(CanvasDimensions(2, 3))
        val characterAtCoordinate = canvas.getPointColour(Point(1, 1))
        characterAtCoordinate shouldBe ' '
    }

    @Test
    fun `getting an invalid coordinate throws CanvasException`() {
        val canvas = Canvas(CanvasDimensions(2, 3))
        shouldThrow<CanvasException> { canvas.getPointColour(Point(1, 4)) }
            .message shouldBe "y coordinate too large"
        shouldThrow<CanvasException> { canvas.getPointColour(Point(1, -4)) }
            .message shouldBe "y coordinate too small"
        shouldThrow<CanvasException> { canvas.getPointColour(Point(3, 2)) }
            .message shouldBe "x coordinate too large"
        shouldThrow<CanvasException> { canvas.getPointColour(Point(-3, 4)) }
            .message shouldBe "x coordinate too small"
    }

    @Test
    fun `change valid coordinate` () {
        val canvas = Canvas(CanvasDimensions(5, 5))
        val point = Point(1, 1)
        canvas.changePointColour(point, 'O')
        canvas.getPointColour(point) shouldBe 'O'
    }

    @Test
    fun `change invalid coordinate throws a CanvasException` () {
        val canvas = Canvas(CanvasDimensions(2, 3))
        shouldThrow<CanvasException> { canvas.changePointColour(Point(1, 4), 'o') }
            .message shouldBe "y coordinate too large"
        shouldThrow<CanvasException> { canvas.changePointColour(Point(1, -4), 'o') }
            .message shouldBe "y coordinate too small"
        shouldThrow<CanvasException> { canvas.changePointColour(Point(3, 2), 'o') }
            .message shouldBe "x coordinate too large"
        shouldThrow<CanvasException> { canvas.changePointColour(Point(-3, 4), 'o') }
            .message shouldBe "x coordinate too small"
    }
}