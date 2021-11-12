package com.dicoding.picodiploma.ticketapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import androidx.core.content.res.ResourcesCompat




class SeatsView : View {
    private val backgroundPaint = Paint()
    private val armrestPaint = Paint()
    private val bottomSeatPaint = Paint()
    private val mBounds = Rect()
    private val numberSeatPaint = Paint(Paint.FAKE_BOLD_TEXT_FLAG)
    private val titlePaint = Paint(Paint.FAKE_BOLD_TEXT_FLAG)

    var seat: Seat? = null
    private val seats: ArrayList<Seat> = arrayListOf()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val text = "Silakan Pilih Kursi"
        titlePaint.apply {
            textSize = 50F
        }
        canvas?.drawText(text, (width / 2F) - 197F, 100F, titlePaint)

        for (seat in seats) {
            drawSeat(canvas, seat)
        }


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        seats.apply {
            add(Seat(1, (width / 2) - 400F, (height / 2) - 700F, "A1",false))
            add(Seat(2, (width / 2) + 100F, (height / 2) - 700F, "A2",false))
            add(Seat(3, (width / 2) - 400F, (height / 2) - 300F, "B1",false))
            add(Seat(4, (width / 2) + 100F, (height / 2) - 300F, "B2",false))
            add(Seat(5, (width / 2) - 400F, (height / 2) + 100F, "C1",false))
            add(Seat(6, (width / 2) + 100F, (height / 2) + 100F, "C2",false))
            add(Seat(7, (width / 2) - 400F, (height / 2) + 500F, "D1",false))
            add(Seat(8, (width / 2) + 100F, (height / 2) + 500F, "D2",false))
        }
    }

    private fun drawSeat(canvas: Canvas?, seat: Seat) {
        canvas?.save()

        // Background
        canvas?.translate(seat.x, seat.y)
        if (seat.isBooked) {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            armrestPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.black, null)
        } else {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.blue_500, null)
            armrestPaint.color = ResourcesCompat.getColor(resources, R.color.blue_700, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.blue_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
        }

        val backgroundPath = Path()
        backgroundPath.addRect(0F, 0F, 300F, 300F, Path.Direction.CCW)
        backgroundPath.addCircle(150F, 50F, 100F, Path.Direction.CCW)
        canvas?.drawPath(backgroundPath, backgroundPaint)

        // Sandaran Tangan
        val armrestPath = Path()
        armrestPath.addRect(0F, 0F, 50F, 300F, Path.Direction.CCW)
        canvas?.drawPath(armrestPath, armrestPaint)
        canvas?.translate(250F, 0F)
        armrestPath.addRect(0F, 0F, 50F, 300F, Path.Direction.CCW)
        canvas?.drawPath(armrestPath, armrestPaint)

        // Bagian Bawah Kursi
        canvas?.translate(-250F, 250F)
        val bottomSeatPath = Path()
        bottomSeatPath.addRect(0F, 0F, 300F, 50F, Path.Direction.CCW)
        canvas?.drawPath(bottomSeatPath, bottomSeatPaint)

        canvas?.translate(0F, -250F)
        numberSeatPaint.apply {
            textSize = 100F
            numberSeatPaint.getTextBounds(seat.name, 0, seat.name.length, mBounds)
        }
        canvas?.drawText(seat.name, 150F - mBounds.centerX(), 150F, numberSeatPaint)

        canvas?.restore()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN -> {
                if (event.x in ((width / 2) - 400F)..((width / 2) - 100F) && event.y in ((height / 2) - 700F)..((height / 2) - 400F)) {
                    booking(0)
                } else if (event.x in ((width / 2) + 100F)..((width / 2) + 400F) && event.y in ((height / 2) - 700F)..((height / 2) - 400F)) {
                    booking(1)
                } else if (event.x in ((width / 2) - 400F)..((width / 2) - 100F) && event.y in ((height / 2) - 300F)..((height / 2).toFloat())) {
                    booking(2)
                } else if (event.x in ((width / 2) + 100F)..((width / 2) + 400F) && event.y in ((height / 2) - 300F)..((height / 2).toFloat())) {
                    booking(3)
                } else if (event.x in ((width / 2) - 400F)..((width / 2) - 100F) && event.y in ((height / 2) + 100F)..((height / 2) + 400F)) {
                    booking(4)
                } else if (event.x in ((width / 2) + 100F)..((width / 2) + 400F) && event.y in ((height / 2) + 100F)..((height / 2) + 400F)) {
                    booking(5)
                } else if (event.x in ((width / 2) - 400F)..((width / 2) - 100F) && event.y in ((height / 2) + 500F)..((height / 2) + 800F)) {
                    booking(6)
                } else if (event.x in ((width / 2) + 100F)..((width / 2) + 400F) && event.y in ((height / 2) + 500F)..((height / 2) + 800F)) {
                    booking(7)
                }
            }
        }
        return true
    }

    private fun booking(position: Int) {
        for (seat in seats) {
            seat.isBooked = false
        }
        seats[position].apply {
            seat = this
            isBooked = true
        }
        invalidate()
    }

}

data class Seat(
    val id: Int,
    val x: Float,
    val y: Float,
    var name: String,
    var isBooked: Boolean
)