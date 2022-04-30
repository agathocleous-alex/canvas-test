import canvas.Canvas
import canvas.Renderer
import io.Input
import io.parser.Command
import io.parser.Parser
import java.util.*

fun main(args: Array<String>) {
    var canvas: Canvas? = null
    var renderer: Renderer? = null
    do {
        print("enter command: ")
        val rawInput = Input.read()
        val command = Parser.extractCommand(rawInput)
        if (canvas == null && command != Command.CREATE && command != Command.QUIT) {
            println("Please create a canvas first")
            continue
        }
        when (command) {
            Command.CREATE -> TODO()
            Command.LINE -> TODO()
            Command.RECTANGLE -> TODO()
            Command.BUCKET_FILL -> TODO()
        }



    } while (command != Command.QUIT)
}