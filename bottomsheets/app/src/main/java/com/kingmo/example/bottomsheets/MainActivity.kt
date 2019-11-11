package com.kingmo.example.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var bottomDialogFragment: LeeRoyBottomDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheetView: ConstraintLayout = findViewById(R.id.sheet_container)
        val expandButton: Button = findViewById(R.id.sheet_expand);

        bottomSheetBehavior = from(bottomSheetView)
        bottomSheetBehavior.setBottomSheetCallback(ShoNuffBottomSheetCallback())

        bottomSheetView.setOnClickListener {toggleBottomSheetOnClick()}
        expandButton.setOnClickListener {toggleBottomSheetOnClick()}

        val show_bottom_dialog: Button = findViewById(R.id.show_bottom_dialog)
        show_bottom_dialog.setOnClickListener { showBottomSheetDialogAsFragment() }
    }

    private fun toggleBottomSheetOnClick() {
        if (bottomSheetBehavior.getState() != STATE_EXPANDED) {
            bottomSheetBehavior.setState(STATE_EXPANDED)
        } else {
            bottomSheetBehavior.setState(STATE_COLLAPSED)
        }
    }

    private fun showBottomSheetDialogAsFragment() {
        bottomDialogFragment = LeeRoyBottomDialogFragment.newInstance()
        bottomDialogFragment?.show(supportFragmentManager, LeeRoyBottomDialogFragment.TAG)
    }

    /*
    * can set custom view of BottomSheetDialog
    */
    private fun showBottomDialog() {
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.fragment_sho_nuff_bottom_sheet_dialog, null, false)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        dialog.show()
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
