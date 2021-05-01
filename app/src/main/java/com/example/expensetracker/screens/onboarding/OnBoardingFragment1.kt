package com.example.expensetracker.screens.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentOnBoarding1Binding

class OnBoardingFragment1 : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentOnBoarding1Binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)

        binding.FABSkip.setOnClickListener {
            findNavController().navigate(OnBoardingFragment1Directions.actionOnBoardingFragment1ToOnBoardingFragment2())
        }

        return binding.root
    }
}