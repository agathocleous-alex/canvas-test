package endToEnd

import domain.Canvas
import domain.Point
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
  fun `drawing a valid horizontal line on an existing canvas`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 0 0 19 0".toByteArray()))
    InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas)
    for(i in 0 until 19) {
      canvas.getPointColour(Point(i, 0)) shouldBe 'X'
    }
  }

  @Test
  fun `drawing a valid vertical line on an existing canvas`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 0 0 0 19".toByteArray()))
    InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas)
    for(i in 0 until 19) {
      canvas.getPointColour(Point(0, i)) shouldBe 'X'
    }
  }

  @Test
  fun `drawing diagonal line on an existing canvas should throw InvalidInputException`() {
    System.setIn(ByteArrayInputStream("C 20 20".toByteArray()))
    val canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(null)
    System.setIn(ByteArrayInputStream("L 0 0 19 19".toByteArray()))
    shouldThrow<InvalidInputException> { InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas) }
  }
}