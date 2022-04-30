package core.io

import io.Input
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream


class InputTest {
    @BeforeEach
    fun `setup standard in with known value`() {
        System.setIn(ByteArrayInputStream("TEST".toByteArray()))
    }

    @Test
    fun `read line from standard in`() {
        Input.read() shouldBe "TEST"
    }
}