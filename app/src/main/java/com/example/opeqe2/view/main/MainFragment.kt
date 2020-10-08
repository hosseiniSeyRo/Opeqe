package com.example.opeqe2.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.example.opeqe2.utils.MyDrawable
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

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
        btnAlignHorizontally.setOnClickListener { viewModel.alignHorizontally() }
    }

    private fun initObservers() {
        viewModel.drawShape.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    mainLayout.addView(createImageView(MyDrawable()))
                }
                false -> {
                }
            }
        })

        viewModel.alignHorizontally.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    val firstChild = (mainLayout as ViewGroup).getChildAt(0)
                    addOrRemoveProperty(firstChild, RelativeLayout.ALIGN_PARENT_RIGHT, true)
                    addOrRemoveProperty(firstChild, RelativeLayout.CENTER_HORIZONTAL, true)

                    for (index in 1 until (mainLayout as ViewGroup).childCount) {
                        val nextChild = (mainLayout as ViewGroup).getChildAt(index)
                        Log.e("RHLog", nextChild.id.toString())
                        val prevChild = (mainLayout as ViewGroup).getChildAt(index - 1)
                        val params = nextChild.layoutParams as RelativeLayout.LayoutParams
                        params.addRule(RelativeLayout.LEFT_OF, prevChild.id)
                        nextChild.layoutParams = params
                    }
                }
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createImageView(myDrawable: MyDrawable): ImageView {
        val mImageView = ImageView(context)
        mImageView.apply {
            id = View.generateViewId()
            layoutParams = ViewGroup.LayoutParams(imageWidth, imageHeight)
            setImageDrawable(myDrawable)
        }
        mImageView.setOnTouchListener(onTouchListener())
        return mImageView
    }

    private fun onTouchListener(): View.OnTouchListener? {
        return View.OnTouchListener(fun(view: View, event: MotionEvent): Boolean {
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val firstChild = (mainLayout as ViewGroup).getChildAt(0)
                    addOrRemoveProperty(firstChild, RelativeLayout.ALIGN_PARENT_RIGHT, false)
                    addOrRemoveProperty(firstChild, RelativeLayout.CENTER_HORIZONTAL, false)

                    for (index in 1 until (mainLayout as ViewGroup).childCount) {
                        val nextChild = (mainLayout as ViewGroup).getChildAt(index)
                        addOrRemoveProperty(nextChild, RelativeLayout.LEFT_OF, false)
                    }

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

    private fun addOrRemoveProperty(view: View, property: Int, flag: Boolean) {
        val layoutParams: RelativeLayout.LayoutParams =
            view.layoutParams as RelativeLayout.LayoutParams
        if (flag) {
            layoutParams.addRule(property);
        } else {
            layoutParams.removeRule(property);
        }
        view.layoutParams = layoutParams;
    }

}