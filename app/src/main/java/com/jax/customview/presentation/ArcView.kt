package com.jax.customview.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.jax.customview.R
import com.jax.customview.utils.MenuList
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class ArcView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    var onArcClickListener: ((index: Int) -> Unit)? = null
    private val mainPainter = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 100f
        color = Color.RED
    }
    private val painterText = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 1f
        color = Color.BLACK
        textSize = 100f
    }
    private val startAngle = -180f
    private val useCenter = false
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
    private val spacer = 1f
    private val elementsCount = colors.size - 1
    private lateinit var centerImageBitMap: Bitmap

    override fun onDraw(canvas: Canvas) {
        drawArcs(canvas)
        drawCenterCircle(canvas)
        drawWords(canvas)
    }

    private fun drawArcs(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 2f - mainPainter.strokeWidth / 2f
        mainPainter.style = Paint.Style.STROKE
        for (i in 0..elementsCount) {
            mainPainter.color = if (i == buttonClicked) Color.GRAY else colors[i]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + sweepAngle * i + spacer,
                sweepAngle - spacer * 2,
                useCenter,
                mainPainter
            )
        }
    }

    private fun drawCenterCircle(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 2f
        mainPainter.color = Color.GRAY
        mainPainter.style = Paint.Style.FILL
        canvas.drawCircle(
            centerX,
            centerY,
            radius / 2,
            mainPainter
        )
    }

    private fun drawWords(canvas: Canvas) {
        MenuList.menus.forEachIndexed { index, word ->
            val angle = evalAngleByIndex(index)
            val (dx, dy) = getXYByAngle(angle)
            val adjustXYText = getExactPositionText(text = word)

            canvas.save()
            canvas.rotate(angle - 95f, dx, dy)

            canvas.drawText(
                word,
                dx - adjustXYText.exactCenterX(),
                dy - adjustXYText.exactCenterY(),
                painterText
            )
            canvas.restore()
        }
    }

    private fun getXYByAngle(angle: Float): Pair<Float, Float> {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width / 2f) - (mainPainter.strokeWidth / 2f)
        val dx = centerX + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
        val dy = centerY + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
        return Pair(dx, dy)
    }

    private fun evalAngleByIndex(index: Int): Float {
        return (360 / elementsCount * index).toFloat() + (360 / elementsCount / 2).toFloat()
    }

    private fun getExactPositionText(text: String): Rect {
        val rect = Rect()
        painterText.getTextBounds(text, 0, text.length, rect)
        return rect
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val centerX = width / 2f
        val centerY = height / 2f
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val dx = event.x - centerX
                val dy = event.y - centerY
                val index = getIndexByCoordinate(dy, dx)
                buttonClicked = index
                onArcClickListener?.invoke(index)
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