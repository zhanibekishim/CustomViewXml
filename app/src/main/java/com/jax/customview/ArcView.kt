package com.jax.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2

class ArcView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    private val paint = Paint()
    private val startAngle = -180f
    private val useCenter = true
    private val colors = listOf(
        Color.RED,
        Color.CYAN,
        Color.YELLOW,
        Color.BLUE,
        Color.DKGRAY,
        Color.MAGENTA
    )
    private val sweepAngle = 360f / colors.size
    private var buttonClicked = -1

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {
        drawArcs(canvas)
    }

    private fun drawArcs(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 2f
        for (i in colors.indices) {
            paint.color = if (i == buttonClicked) Color.GRAY else colors[i]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + sweepAngle * i,
                sweepAngle,
                useCenter,
                paint
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val centerX = width / 2f
        val centerY = height / 2f
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val dx = event.x - centerX
                val dy = event.y - centerY
                buttonClicked = getIndexByCoordinate(dy, dx)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                buttonClicked = -1
                invalidate()
            }
        }
        return true
    }

    private fun getIndexByCoordinate(y: Float, x: Float): Int {
        val angle = Math.toDegrees(atan2(-y.toDouble(), -x.toDouble()))
        val adjustedAngle = (angle + 360) % 360
        return (adjustedAngle / sweepAngle).toInt()
    }
}
