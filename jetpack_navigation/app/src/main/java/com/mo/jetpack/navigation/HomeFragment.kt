package com.mo.jetpack.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.mo.jetpack.navigation.models.ContentModel

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_home, container, false)
        rootView.findViewById<Button>(R.id.content_button).setOnClickListener {
            val contentAction = HomeFragmentDirections.actionHomeFragmentToContentFragment(ContentModel(resources.getString(R.string.content_title),
                resources.getString(R.string.content_body),
                "Mike Jones"))
            it.findNavController().navigate(contentAction)
        }
        return rootView
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
