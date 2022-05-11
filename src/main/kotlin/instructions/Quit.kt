package instructions

import domain.Canvas
import kotlin.system.exitProcess

class Quit(private val status: Int) : Instruction() {
  override fun executeInstruction(canvas: Canvas?): Canvas {
    println("Thank you for using TechneDraw!")
    exitProcess(this.status)
  }
}