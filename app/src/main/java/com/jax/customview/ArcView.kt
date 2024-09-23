package com.jax.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ArcView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    private val paint = Paint()
    private val startAngle = -180f
    private val colors = listOf(Color.RED,Color.CYAN,Color.YELLOW)
    init{
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }
    override fun onDraw(canvas: Canvas) {
        drawArcs(canvas)
    }

    private fun drawArcs(canvas: Canvas) {
        val centerX = width/2f
        val centerY = width/2f

        canvas.drawArc(
            centerX,
            centerY,
            centerX,
            centerY,
            startAngle,
            360f,
            true,
            paint
        )
    }

}