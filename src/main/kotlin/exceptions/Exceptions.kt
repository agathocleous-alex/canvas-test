package exceptions

class CanvasException(message: String) : RuntimeException(message)
class InvalidInputException(message: String) : RuntimeException(message)
class NoCanvasException(message: String) : RuntimeException(message)

class ExceptionBuilder {
  companion object {
    fun getCanvasException(message: String) : CanvasException {
      return CanvasException(message)
    }

    fun getInvalidInputException(message: String) : InvalidInputException {
      return InvalidInputException(message)
    }

    fun getNoCanvasException() : NoCanvasException {
      return NoCanvasException("There does not appear to be a canvas. Please make one before trying to draw")
    }
  }
}