package parser

import exceptions.InvalidInputException
import instructions.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

class InstructionFactoryTest {

    @Test
    fun `parsing a valid input returns the corresponding Instruction` () {
        InstructionFactory.buildInstruction("C 20 20").shouldBeTypeOf<Create>()
        InstructionFactory.buildInstruction("L 1 1 2 1").shouldBeTypeOf<Line>()
        InstructionFactory.buildInstruction("R 1 1 2 1").shouldBeTypeOf<Rectangle>()
        InstructionFactory.buildInstruction("B 1 1 C").shouldBeTypeOf<BucketFill>()
        InstructionFactory.buildInstruction("Q").shouldBeTypeOf<Quit>()

        InstructionFactory.buildInstruction("c 20 20").shouldBeTypeOf< Create>()
        InstructionFactory.buildInstruction("l 1 1 2 1").shouldBeTypeOf< Line>()
        InstructionFactory.buildInstruction("r 1 1 2 1").shouldBeTypeOf< Rectangle>()
        InstructionFactory.buildInstruction("b 1 1 C").shouldBeTypeOf< BucketFill>()
        InstructionFactory.buildInstruction("q").shouldBeTypeOf< Quit>()
    }

    @Test
    fun `parsing an invalid command should throw InvalidInputException` () {
        shouldThrow<InvalidInputException> { InstructionFactory.buildInstruction("Y y 4 5 3") }
            .message shouldBe "Unrecognised Command"
    }

    @Test
    fun `parsing an empty input should throw InvalidInputException`() {
        shouldThrow<InvalidInputException> { InstructionFactory.buildInstruction(" ") }
    }
}
