package endToEnd

import domain.Canvas
import domain.Point
import exceptions.CanvasException
import exceptions.InvalidInputException
import exceptions.NoCanvasException
import instructions.InstructionFactory
import io.Input
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class EndToEndTest {

  @Test
  fun `creating a canvas from System in value C 20 20 creates a valid canvas `() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    canvas.shouldBeTypeOf<Canvas>()
    canvas.getHeight() shouldBe 20
    canvas.getWidth() shouldBe 20
  }

  @Test
  fun `creating a canvas from System in value C 20 20 with extra whitespace still creates a valid canvas `() {
    System.setIn(ByteArrayInputStream("   C    20     20    ".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    canvas.shouldBeTypeOf<Canvas>()
    canvas.getHeight() shouldBe 20
    canvas.getWidth() shouldBe 20
  }

  @Test
  fun `drawing anything with no canvas created will throw NoCanvasException`() {
    System.setIn(ByteArrayInputStream("L 20 20 20 20".toByteArray()))
    shouldThrow<NoCanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(null) }
    System.setIn(ByteArrayInputStream("R 20 20 20 20".toByteArray()))
    shouldThrow<NoCanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(null) }
    System.setIn(ByteArrayInputStream("B 30 30 o".toByteArray()))
    shouldThrow<NoCanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(null) }
  }

  @Test
  fun `drawing a horizontal line from x = 1 to x = max on an existing canvas draws line from origin to max on first row` () {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 1 1 20 1".toByteArray()))
    InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas)
    for(i in 0 until canvas.getWidth()) {
      canvas.getPointColour(Point(i, 0)) shouldBe 'X'
    }
  }

  @Test
  fun `drawing a horizontal line from x = 1 to a coordinate larger than the maximum width of the canvas tells the user that the x coordinate is too large`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 1 1 21 1".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "x coordinate too large"
  }

  @Test
  fun `drawing a horizontal line from x = 0 on an existing canvas tells the user that the x-coordinate is too small`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 0 0 19 0".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "x coordinate too small"
  }

  @Test
  fun `draw a horizontal line on the last row of the canvas from (1, 1) to (1, max)`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 1 20 20 20".toByteArray()))
    InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas)
    for(i in 0 until canvas.getWidth()) {
      canvas.getPointColour(Point(i, canvas.getHeight() - 1)) shouldBe 'X'
    }
  }

  @Test
  fun `drawing a valid vertical line on an existing canvas from (1, 1) to (1, max)`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 1 1 1 20".toByteArray()))
    InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas)
    for(i in 0 until canvas.getHeight()) {
      canvas.getPointColour(Point(0, i)) shouldBe 'X'
    }
  }

  @Test
  fun `drawing a valid rectangle from origin to max`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("R 1 1 20 20".toByteArray()))
    InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas)
    //bottom
    for(i in 0 until canvas.getWidth()) {
      canvas.getPointColour(Point(i, canvas.getHeight() - 1)) shouldBe 'X'
    }
    //top
    for(i in 0 until canvas.getWidth()) {
      canvas.getPointColour(Point(i, 0)) shouldBe 'X'
    }
    //Left
    for(i in 0 until canvas.getHeight()) {
      canvas.getPointColour(Point(0, i)) shouldBe 'X'
    }
    //Right
    for(i in 0 until canvas.getHeight()) {
      canvas.getPointColour(Point(canvas.getWidth() - 1, i)) shouldBe 'X'
    }
  }

  @Test
  fun `drawing a rectangle with beginning at (0, 0) tells the user the x coordinate was too small`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("R 0 0 20 20".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "x coordinate too small"
  }

  @Test
  fun `drawing a rectangle with beginning at (1, 0) tells the user the y coordinate was too small`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("R 1 0 20 20".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "y coordinate too small"
  }

  @Test
  fun `drawing diagonal line on an existing canvas should throw InvalidInputException`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 1 1 19 19".toByteArray()))
    shouldThrow<InvalidInputException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
  }

  @Test
  fun `filling in point (0, 0) will tell the user that the x coordinate was too small`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("B 0 0 o".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "x coordinate too small"
  }

  @Test
  fun `filling in point (1, 0) will tell the user that the x coordinate was too small`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("B 1 0 o".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "y coordinate too small"
  }

  @Test
  fun `filling in point (max+1, max+1) will tell the user that the x coordinate was too large`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("B 21 21 o".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "x coordinate too large"
  }

  @Test
  fun `filling in point (max, max+1) will tell the user that the x coordinate was too large`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("B 20 21 o".toByteArray()))
    shouldThrow<CanvasException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
      .message shouldBe "y coordinate too large"
  }
}