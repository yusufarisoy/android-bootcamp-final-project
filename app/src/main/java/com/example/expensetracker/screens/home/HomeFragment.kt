package com.example.expensetracker.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.database.AppDatabase
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment()
{
    private lateinit var adapter: ExpenseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).databaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val currentCurrency = HomeFragmentArgs.fromBundle(requireArguments()).currentCurrency
        viewModel.setCurrentCurrency(currentCurrency)
        binding.homeCurrencies.check(when(currentCurrency)
        {
            1 -> binding.radioButtonTRY.id
            2 -> binding.radioButtonUSD.id
            3 -> binding.radioButtonGBP.id
            else -> binding.radioButtonEUR.id
        })

        viewModel.showConnectionStatus.observe(viewLifecycleOwner, {
            if(it == true)
            {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.no_connection_warning), Snackbar.LENGTH_LONG).show()
                viewModel.connectionStatusShowDone()
            }
        })

        adapter = ExpenseAdapter(ExpenseClickListener {
            viewModel.onExpenseClicked(it)
        }, CurrencyChangeListener {
                viewModel.calculateTotalExpense(it)
        }, viewModel.getCurrentCurrency(), viewModel.getCurrentExchangeRate())
        binding.recyclerViewExpenses.adapter = adapter

        viewModel.expenses.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
                var sum = 0f
                it.forEach { i -> sum += i.cost }
                viewModel.calculateTotalExpense(sum)
            }
        })

        viewModel.userName.observe(viewLifecycleOwner, {
            binding.textViewUserName.text = it
        })

        viewModel.userGender.observe(viewLifecycleOwner, {
            binding.textViewUserGender.text = when(it)
            {
                0 -> "Hanım"
                1 -> "Bey"
                else -> ""
            }
        })

        viewModel.totalExpense.observe(viewLifecycleOwner, {
            binding.textViewTotalExpense.text = it
        })

        viewModel.currentCurrency.observe(viewLifecycleOwner, {
            adapter.changeCurrency(viewModel.getCurrentCurrency(), viewModel.getCurrentExchangeRate())
        })

        binding.homeCurrencies.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonEUR.id -> viewModel.setCurrentCurrency(0)
                binding.radioButtonTRY.id -> viewModel.setCurrentCurrency(1)
                binding.radioButtonUSD.id -> viewModel.setCurrentCurrency(2)
                binding.radioButtonGBP.id -> viewModel.setCurrentCurrency(3)
            }
        }

        viewModel.navigateToExpenseDetail.observe(viewLifecycleOwner, {
            if(it != null)
            {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToExpenseDetailFragment(it, viewModel.getCurrentCurrency()))
                viewModel.navigationDone()
            }
        })

        binding.constraintLayoutHeader.setOnClickListener{ this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment()) }

        binding.FABAddExpense.setOnClickListener { this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToExpenseCreateFragment()) }

        return binding.root
    }
}