package com.mo.jetpack.navigation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mo.jetpack.navigation.databinding.FragmentContentBinding

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
        viewBinding.contentViewModel = navigationArgs.contentModel
    }

    companion object {
        val TAG = ContentFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = ContentFragment()
    }
}
