import canvas.Canvas
import canvas.Renderer
import exceptions.CanvasException
import io.Input
import io.parser.Command
import io.parser.Parser

fun main(args: Array<String>) {
    Main.executionLoop()
}

class Main {
    companion object {
        private var canvas: Canvas? = null
        private var renderer: Renderer? = null
        private const val invalidCommandMessage = "Sorry, that command isn't recognised. Please try one of the list of valid commands: \n" +
                "C x y : creates a canvas of x width and y height \n" +
                "L x1 y1 x2 y2 : draws a line from point to point \n" +
                "R x1 y1 x2 y2 : draws a rectangle from point to point \n" +
                "B x y c : fills an enclosed around x and y with the colour c \n" +
                "Q : quits the program"
        private val invalidCanvasException = CanvasException("Please create a canvas before trying anything else!")

        fun executionLoop() {
            try {
                print("enter command: ")
                val input = Input.read()
                val command = Parser.parseCommand(input)
                if(command == Command.QUIT) {
                    println("Thank you for using TechneDraw")
                    return
                }
                val result = executeCommand(command, input)
                if(result.isFailure) {
                    println(result.exceptionOrNull()!!.message)
                    executionLoop()
                }
                println(canvas)
                executionLoop()
            } catch(re: RuntimeException) {
                println(re.message)
                executionLoop()
            }
        }

        private fun executeCommand(command: Command, input: String) : Result<Command> {
            if(!canvasExists() && command != Command.CREATE && command != Command.QUIT) {
                return Result.failure(invalidCanvasException)
            }
            when (command) {
                Command.CREATE -> {
                    canvas = Canvas(Parser.parseCanvasDimensions(input))
                    renderer = Renderer(canvas!!)
                    println("New canvas created!")
                    return Result.success(command)
                }
                Command.LINE -> {
                    renderer!!.drawShape(Parser.parseShapeParameters(input))
                    println("Line drawn successfully!")
                    return Result.success(command)
                }
                Command.RECTANGLE -> {
                    renderer!!.drawShape(Parser.parseShapeParameters(input))
                    println("Rectangle drawn successfully")
                    return Result.success(command)
                }
                Command.BUCKET_FILL -> {
                    renderer!!.fill(Parser.parseBucketFillParameters(input))
                    println("Area filled successfully")
                    return Result.success(command)
                }
                Command.QUIT -> {
                    return Result.success(command)
                }
//                else -> {return Result.failure(InvalidInputException(invalidCommandMessage))}
            }
        }

        private fun canvasExists() = canvas != null
    }
}
