package com.example.expensetracker.screens.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentSplashBinding

private const val PREFERENCES_FILE: String = "SharedPreferences"
private const val PREFERENCE_ON_BOARDING: String = "isOnBoardingCompleted"

class SplashFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentSplashBinding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        launchApp()

        return binding.root
    }

    private fun launchApp()
    {
        val sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
        val isOnBoardingCompleted = sharedPreferences.getBoolean(PREFERENCE_ON_BOARDING, false)

        Handler(Looper.getMainLooper()).postDelayed({
            if(isOnBoardingCompleted)
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            else
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment1())
        }, 1200)
    }
}