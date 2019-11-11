package com.kingmo.example.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LeeRoyBottomDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sho_nuff_bottom_sheet_dialog, container, false)
    }

    companion object {
        public val TAG: String = "LeeRoyBottomDialogFragment"
        @JvmStatic
        fun newInstance() = LeeRoyBottomDialogFragment()
    }
}
