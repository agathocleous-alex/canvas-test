package exceptions

class CanvasException(message: String) : RuntimeException(message)
class InvalidInputException(message: String) : RuntimeException(message)
class ParserException(message: String) : RuntimeException(message)