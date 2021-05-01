package com.example.expensetracker.screens.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentOnBoarding2Binding

class OnBoardingFragment2 : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentOnBoarding2Binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)

        binding.FABSkip.setOnClickListener {
            findNavController().navigate(OnBoardingFragment2Directions.actionOnBoardingFragment2ToOnBoardingFragment3())
        }

        return binding.root
    }
}