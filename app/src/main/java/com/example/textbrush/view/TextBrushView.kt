package com.example.textbrush.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class TextBrushView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var path = Path()
    private var textSize = 30f
    private val segments = mutableListOf<Path>()
    private val touchPoints = mutableListOf<PointF>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("TouchEvent", "Event: ${event.action}, X: ${event.x}, Y: ${event.y}")
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                touchPoints.add(PointF(event.x, event.y))
                invalidate()  // Request a redraw
            }
            MotionEvent.ACTION_UP -> {
                segments.add(Path(path))
                path.reset()
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        super.onDraw(canvas)

        val paint = Paint().apply {
            color = Color.WHITE
            textSize = 30f
            style = Paint.Style.FILL
        }

        for (point in touchPoints) {
            canvas.drawText("HELLO", point.x, point.y, paint)
        }
    }

    fun undo() {
        if (segments.isNotEmpty()) {
            segments.removeAt(segments.size - 1)
            invalidate()
        }
    }
}