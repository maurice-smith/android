package com.mo.jetpack.navigation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class ContentFragment : Fragment() {
     private val navigationArgs: ContentFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationArgs.contentModel
        Log.d(TAG, "content title: ${navigationArgs.contentModel?.title}")
        Log.d(TAG, "content author: ${navigationArgs.contentModel?.author}")
        Log.d(TAG, "content body:\n${navigationArgs.contentModel?.body}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    companion object {
        val TAG = ContentFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = ContentFragment()
    }
}
