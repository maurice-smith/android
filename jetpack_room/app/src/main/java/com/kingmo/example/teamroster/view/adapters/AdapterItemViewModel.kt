package com.kingmo.example.teamroster.view.adapters

import androidx.annotation.LayoutRes

interface AdapterItemViewModel {
    @LayoutRes
    fun getLayoutResId(): Int
    fun getBindingVariable(): Int
}