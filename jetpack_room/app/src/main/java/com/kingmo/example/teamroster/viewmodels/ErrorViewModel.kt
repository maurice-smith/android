package com.kingmo.example.teamroster.viewmodels

import android.view.View
import com.kingmo.example.teamroster.utils.EMPTY_STRING

class ErrorViewModel {
    var errorVisibility: Int = View.GONE

    var message: String = EMPTY_STRING
        get() = field
        set(value) {
            field = value
            errorVisibility = (if (value.trim().isNotEmpty()) View.VISIBLE else View.GONE)
        }
}