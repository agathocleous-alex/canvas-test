package instructions

import domain.Canvas
import domain.CanvasDimensions
import exceptions.ExceptionBuilder

class Create(private val dimensions: CanvasDimensions) : Instruction() {

  companion object{
    const val maxWidth = 500
    const val maxHeight = 500
  }

  override fun executeInstruction(canvas: Canvas?) : Canvas {
    validateInputDimensions()
    return Canvas(dimensions = dimensions)
  }

  private fun validateInputDimensions() {
    if (dimensions.width <= 0 || dimensions.height <= 0)
      throw ExceptionBuilder.getCanvasException("Canvas cannot be created; negative or 0 values detected")
    if (dimensions.width > maxWidth || dimensions.height > maxHeight)
      throw ExceptionBuilder.getCanvasException(
        "Canvas too large. Max width = $maxWidth, your width = ${dimensions.width}, " +
                "max height = $maxHeight, your height = ${dimensions.height}"
      )
  }
}