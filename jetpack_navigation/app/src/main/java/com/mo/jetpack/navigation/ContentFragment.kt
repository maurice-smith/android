package com.mo.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mo.jetpack.navigation.databinding.FragmentContentBinding
import com.mo.jetpack.navigation.models.ContentModel

class ContentFragment : Fragment() {
    private val navigationArgs: ContentFragmentArgs by navArgs()
    private lateinit var viewBinding: FragmentContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (navigationArgs.contentModel != null) {
            viewBinding.contentViewModel = navigationArgs.contentModel
        } else {
            viewBinding.contentViewModel = ContentModel("Default Content", "Default body", "None")
        }
    }
}
