package com.example.expensetracker.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentProfileBinding

class ProfileFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ProfileViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        viewModel.userName.observe(viewLifecycleOwner, {
            binding.textInputEditTextName.setText(it)
        })

        viewModel.userGender.observe(viewLifecycleOwner, {
            if(it == 0)
                binding.radioGroupGender.check(binding.radioButtonFemale.id)
            else if(it == 1)
                binding.radioGroupGender.check(binding.radioButtonMale.id)
        })

        binding.buttonSaveChanges.setOnClickListener {
            val userName = binding.textInputEditTextName.text.toString()
            val userGender = when(binding.radioGroupGender.checkedRadioButtonId)
            {
                binding.radioButtonMale.id -> 1
                binding.radioButtonFemale.id -> 0
                else -> -1
            }

            viewModel.saveChanges(userName, userGender)
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            if(it == true)
            {
                this.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
                viewModel.navigationDone()
            }
        })

        return binding.root
    }
}