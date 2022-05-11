package io

import domain.Canvas

class Renderer {
    companion object {
        fun drawCanvas(canvas: Canvas) {
            val stringBuilder = StringBuilder()
            buildHorizontalBar(stringBuilder, canvas.getWidth())
            canvas.getMatrix().forEach { row ->
                stringBuilder.append("#")
                row.value.forEach { column ->
                    stringBuilder.append(column)
                }
                stringBuilder.append("#")
                stringBuilder.append("\n")
            }
            buildHorizontalBar(stringBuilder, canvas.getWidth())
            println(stringBuilder.toString())
        }

        private fun buildHorizontalBar(stringBuilder: StringBuilder, width: Int) {
            for (i in 0..width + 1) {
                stringBuilder.append("#")
            }
            stringBuilder.append("\n")
        }
    }
}
