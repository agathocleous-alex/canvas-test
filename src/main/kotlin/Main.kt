import domain.Canvas
import io.Renderer
import instructions.InstructionFactory
import io.Input

fun main(args: Array<String>) {
    println(
        """
            C x y : creates a canvas of x width and y height
            L x1 y1 x2 y2 : draws a line from point to point. Currently we only support vertical or horizontal lines.
            R x1 y1 x2 y2 : draws a rectangle from point to point
            B x y c : fills an enclosed around x and y with the colour c
            Q : quits the program
            Lets get started!
        """.trimIndent())
    Main.executionLoop()
}

class Main {
    companion object {
        private var canvas: Canvas? = null

        fun executionLoop() {
            try {
                print("enter command: ")
                canvas = InstructionFactory.buildInstruction(Input.read()).executeInstruction(canvas)
                Renderer.drawCanvas(canvas!!)
                executionLoop()
            } catch(re: RuntimeException) {
                println(re.message)
                executionLoop()
            }
        }
    }
}
