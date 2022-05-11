package io

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream


class InputTest {

    @Test
    fun `read line from standard in`() {
        System.setIn(ByteArrayInputStream("T  E             S    T  ".toByteArray()))
        Input.read() shouldBe "T E S T"

        System.setIn(ByteArrayInputStream("".toByteArray()))
        shouldThrow<NullPointerException> { Input.read() }

        System.setIn(ByteArrayInputStream("C  4  4".toByteArray()))
        Input.read() shouldBe "C 4 4"

        System.setIn(ByteArrayInputStream("     ".toByteArray()))
        Input.read() shouldBe ""

    }
}