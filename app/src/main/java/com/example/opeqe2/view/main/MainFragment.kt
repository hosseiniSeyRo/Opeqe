package com.example.opeqe2.view.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.opeqe2.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val imageWidth = 200
    private val imageHeight = 200
    private var xDelta = 0
    private var yDelta = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        clickHandler()
        initObservers()
    }

    private fun clickHandler() {
        btnDrawShape.setOnClickListener { viewModel.drawShape() }
    }

    private fun initObservers() {
        viewModel.drawShape.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    mainLayout.addView(createImageView(createShapeDrawable(OvalShape())))
                }
                false -> {
                }
            }
        })
    }

    private fun createImageView(myDrawable: ShapeDrawable): ImageView {
        val mImageView = ImageView(context)
        mImageView.apply {
            id = View.generateViewId()
            layoutParams = ViewGroup.LayoutParams(imageWidth, imageHeight)
            setImageDrawable(myDrawable)
        }
        mImageView.setOnTouchListener(onTouchListener())
        return mImageView
    }

    private fun createShapeDrawable(shape: Shape): ShapeDrawable {
        val badge = ShapeDrawable(shape)
        badge.intrinsicWidth = 200
        badge.intrinsicHeight = 200
        badge.paint.color = Color.RED
        return badge
    }

    private fun onTouchListener(): View.OnTouchListener? {
        return View.OnTouchListener(fun(view: View, event: MotionEvent): Boolean {
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
//                    addOrRemoveProperty(newImageView, RelativeLayout.ALIGN_PARENT_RIGHT, false)
//                    addOrRemoveProperty(newImageView, RelativeLayout.ALIGN_PARENT_BOTTOM, false)

                    val lParams = view.layoutParams as RelativeLayout.LayoutParams
                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin
                }
                MotionEvent.ACTION_UP ->
                    Toast.makeText(
                        this.context,
                        "I'm here!",
                        Toast.LENGTH_SHORT
                    ).show()
                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = view
                        .layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin = x - xDelta
                    layoutParams.topMargin = y - yDelta
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    view.layoutParams = layoutParams
                }
            }
            mainLayout.invalidate()
            return true
        })
    }
}