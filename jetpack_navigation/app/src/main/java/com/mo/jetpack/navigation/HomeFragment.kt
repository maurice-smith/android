package com.mo.jetpack.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.mo.jetpack.navigation.models.ContentModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        content_button.setOnClickListener {
            val contentAction = HomeFragmentDirections.actionHomeFragmentToContentFragment(ContentModel(resources.getString(R.string.content_title),
                resources.getString(R.string.content_body),
                "Mike Jones"))
            it.findNavController().navigate(contentAction)
        }
    }
}
