package com.kingmo.example.bottomsheets

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheetView: ConstraintLayout = findViewById(R.id.sheet_container)
        val expandButton: Button = findViewById(R.id.sheet_expand);

        bottomSheetBehavior = from(bottomSheetView)
        bottomSheetBehavior.setBottomSheetCallback(ShoNuffBottomSheetCallback())

        bottomSheetView.setOnClickListener {toggleBottomSheetOnClick()}
        expandButton.setOnClickListener {toggleBottomSheetOnClick()}
    }

    private fun toggleBottomSheetOnClick() {
        if (bottomSheetBehavior.getState() != STATE_EXPANDED) {
            bottomSheetBehavior.setState(STATE_EXPANDED)
        } else {
            bottomSheetBehavior.setState(STATE_COLLAPSED)
        }
    }

    class ShoNuffBottomSheetCallback: BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            //no-op
        }

        override fun onStateChanged(bottomSheet: View, behaviorState: Int) {
            val showHideButton: Button = bottomSheet.findViewById(R.id.sheet_expand)

            if (behaviorState == STATE_EXPANDED) {
                showHideButton.setText(R.string.hide)
            } else if (behaviorState == STATE_COLLAPSED) {
                showHideButton.setText(R.string.expand)
            }
        }
    }
}
