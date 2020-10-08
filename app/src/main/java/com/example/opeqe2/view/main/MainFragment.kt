package com.example.opeqe2.view.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.opeqe2.MyApplication
import com.example.opeqe2.R
import com.example.opeqe2.utils.CustomTouchListener
import com.example.opeqe2.utils.dpToPixel
import com.example.opeqe2.view.ShapeFactory
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(R.layout.main_fragment) {

    private var counter: Int = 1
    private lateinit var viewModel: MainViewModel
    private val imageWidth = 80
    private val imageHeight = 80

    @Inject
    lateinit var shapeFactory: ShapeFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setClickListeners()
        observerViewModel()
    }

    private fun setClickListeners() {
        btnDrawShape.setOnClickListener { viewModel.onDrawShapeClicked() }
        btnAlignHorizontally.setOnClickListener { viewModel.onAlignHorizontallyClicked() }
        btnAlignVertically.setOnClickListener { viewModel.onAlignVerticallyClicked() }
    }

    private fun observerViewModel() {
        viewModel.drawShape.observe(viewLifecycleOwner) {
            mainLayout.addView(createImageView(shapeFactory.getShape()))
            Toast.makeText(requireContext(), "${counter++} shape added", Toast.LENGTH_SHORT).show()
        }

        viewModel.alignHorizontally.observe(viewLifecycleOwner) {
            for (index in 0 until (mainLayout as ViewGroup).childCount) {
                val nextChild = (mainLayout as ViewGroup).getChildAt(index)
                nextChild.animate()
                    .y(0F)
                    .setDuration(0)
                    .start()
            }
        }

        viewModel.alignVertically.observe(viewLifecycleOwner) {
            for (index in 0 until (mainLayout as ViewGroup).childCount) {
                val nextChild = (mainLayout as ViewGroup).getChildAt(index)
                nextChild.animate()
                    .x(0F)
                    .setDuration(0)
                    .start()
            }
        }
    }

    private fun createImageView(drawable: Drawable): ImageView {
        val shapeImageView = ImageView(context)
        shapeImageView.apply {
            layoutParams = ViewGroup.LayoutParams(
                imageWidth.dpToPixel(requireContext()),
                imageHeight.dpToPixel(requireContext())
            )
            setImageDrawable(drawable)
            setOnTouchListener(CustomTouchListener(mainLayout.width, mainLayout.height))
        }
        return shapeImageView
    }
}