package io.parser

import exceptions.InvalidInputException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.Test

class ParserTest {

    @Test
    fun `parsing of each kind of command is successful` () {
        Parser.extractCommand("C ") shouldBe Command.CREATE
        Parser.extractCommand("L ") shouldBe Command.LINE
        Parser.extractCommand("R ") shouldBe Command.RECTANGLE
        Parser.extractCommand("B ") shouldBe Command.BUCKET_FILL
        Parser.extractCommand("Q ") shouldBe Command.QUIT
    }

    @Test
    fun `parsing an unrecognised command should throw InvalidInputException` () {
        shouldThrow<InvalidInputException> { Parser.extractCommand("Y ") }
            .message shouldBe "Unrecognised Command"
    }

    @Test
    fun `extract canvas dimensions from valid input` () {
        val canvasDimensions = Parser.parseCanvasDimensions("2 3")
        canvasDimensions.width shouldBe 2
        canvasDimensions.height shouldBe 3
    }

    @Test
    fun `extracting canvas dimensions with invalid parameters should throw InvalidInputException` () {
        shouldThrow<InvalidInputException> { Parser.parseCanvasDimensions("2 ") }
            .message shouldStartWith "Invalid parameter detected."
        shouldThrow<InvalidInputException> { Parser.parseCanvasDimensions("4 5 4") }
            .message shouldStartWith  "Invalid number of parameters"
    }

    @Test
    fun `extracting line coordinates from valid input` () {
        val points = Parser.parseShapeParameters("4 2 7 2")
        points.first.xCoord shouldBe 4
        points.first.yCoord shouldBe 2
        points.second.xCoord shouldBe 7
        points.second.yCoord shouldBe 2
    }

    @Test
    fun `extracting lines from input that is right to left should flip the points` () {
        val points = Parser.parseShapeParameters("7 2 4 2")
        points.first.xCoord shouldBe 4
        points.first.yCoord shouldBe 2
        points.second.xCoord shouldBe 7
        points.second.yCoord shouldBe 2
    }

    @Test
    fun `extracting line coordinates with invalid number of parameters should throw InvalidInputException` () {
        shouldThrow<InvalidInputException> { Parser.parseShapeParameters("7 9 9 3 9") }
        shouldThrow<InvalidInputException> { Parser.parseShapeParameters("8 8") }
    }

    @Test
    fun `parsing bucket fill point and colour with valid input`() {
        val bucketFillParameters = Parser.parseBucketFillParameters("4 5 C")
        bucketFillParameters.point.xCoord shouldBe 4
        bucketFillParameters.point.yCoord shouldBe 5
        bucketFillParameters.colour shouldBe 'C'
    }

    @Test
    fun `parsing bucket fill point and colour with invalid parameters should throw InvalidInputException`() {
        shouldThrow<InvalidInputException> { Parser.parseBucketFillParameters("4 5 AA") }
            .message shouldBe "Invalid colour; colour must be a single character"

        shouldThrow<InvalidInputException> { Parser.parseBucketFillParameters("D 5 A") }
            .message shouldStartWith "Invalid parameter detected."
    }
}
