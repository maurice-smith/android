package com.kingmo.example.teamroster.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.kingmo.example.teamroster.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_no_results.*

@AndroidEntryPoint
class NoResultsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        no_results_add_button.setOnClickListener {
            it.findNavController().navigate(NoResultsFragmentDirections.actionNoResultsFragmentToAddPlayerFragment())
        }
    }
}