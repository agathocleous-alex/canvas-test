package io.parser

import canvas.Canvas
import exceptions.InvalidInputException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

class ParserTest {

    @Test
    fun `parsing a valid input character results in the correct corresponding command` () {
        Parser.parseCommand("C") shouldBe Command.CREATE
        Parser.parseCommand("L") shouldBe Command.LINE
        Parser.parseCommand("R") shouldBe Command.RECTANGLE
        Parser.parseCommand("B") shouldBe Command.BUCKET_FILL
        Parser.parseCommand("Q") shouldBe Command.QUIT
    }

    @Test
    fun `parsing an unrecognised command should throw InvalidInputException` () {
        shouldThrow<InvalidInputException> { Parser.parseCommand("Y") }
            .message shouldBe "Unrecognised Command"
    }

    @Test
    fun `parsing canvas dimensions from valid input should result in a CanvasDimensions object with correct dimensions` () {
        val canvasDimensions = Parser.parseCanvasDimensions("C 2 3")
        canvasDimensions.shouldBeTypeOf<Canvas.CanvasDimensions>()
        canvasDimensions.width shouldBe 2
        canvasDimensions.height shouldBe 3
    }

    @Test
    fun `parsing canvas dimensions with invalid parameters should throw InvalidInputException` () {
        shouldThrow<InvalidInputException> { Parser.parseCanvasDimensions("2 3 89") }
            .message shouldStartWith "Invalid input detected."
    }

    @Test
    fun `parsing shape coordinates from valid input should result in a pair of Points` () {
        val points = Parser.parseShapeParameters("L 4 2 7 2")
        points.shouldBeTypeOf<Pair<Canvas.Point, Canvas.Point>>()
        points.first.xCoord shouldBe 4
        points.first.yCoord shouldBe 2
        points.second.xCoord shouldBe 7
        points.second.yCoord shouldBe 2
    }

    @Test
    fun `parsing lines from input that is right to left should flip the points` () {
        val points = Parser.parseShapeParameters("L 7 2 4 2")
        points.first.xCoord shouldBe 4
        points.first.yCoord shouldBe 2
        points.second.xCoord shouldBe 7
        points.second.yCoord shouldBe 2
    }

    @Test
    fun `parsing line coordinates with invalid input should throw InvalidInputException` () {
        shouldThrow<InvalidInputException> { Parser.parseShapeParameters("L 7 9 9 3 9") }
            .message shouldStartWith "Invalid input detected."
    }

    @Test
    fun `parsing bucket fill point and colour with valid input should result in BucketFillParameters with correct fields`() {
        val bucketFillParameters = Parser.parseBucketFillParameters("B 4 5 C")
        bucketFillParameters.shouldBeTypeOf<Canvas.BucketFillParameters>()
        bucketFillParameters.point.xCoord shouldBe 4
        bucketFillParameters.point.yCoord shouldBe 5
        bucketFillParameters.colour shouldBe 'C'
    }

    @Test
    fun `parsing bucket fill point and colour with invalid parameters should throw InvalidInputException`() {
        shouldThrow<InvalidInputException> { Parser.parseBucketFillParameters("B D 5 A") }
            .message shouldStartWith "Invalid input detected."
    }
}
