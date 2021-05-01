package com.example.expensetracker.screens.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentOnBoarding3Binding

private const val PREFERENCES_FILE: String = "SharedPreferences"
private const val PREFERENCE_ON_BOARDING: String = "isOnBoardingCompleted"
private const val PREFERENCE_NAME: String = "userName"

class OnBoardingFragment3 : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentOnBoarding3Binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)

        binding.FABStart.setOnClickListener {
            val name = binding.textInputEditTextName.text.toString()
            if(name != "")
            {
                val sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean(PREFERENCE_ON_BOARDING, true)
                    putString(PREFERENCE_NAME, name)
                    apply()
                }
                findNavController().navigate(OnBoardingFragment3Directions.actionOnBoardingFragment3ToHomeFragment())
            }
        }

        return binding.root
    }
}