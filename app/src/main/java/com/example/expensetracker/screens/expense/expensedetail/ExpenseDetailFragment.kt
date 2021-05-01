package com.example.expensetracker.screens.expense.expensedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.database.AppDatabase
import com.example.expensetracker.databinding.FragmentExpenseDetailBinding

class ExpenseDetailFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentExpenseDetailBinding = FragmentExpenseDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).databaseDao

        val expense = ExpenseDetailFragmentArgs.fromBundle(requireArguments()).selectedExpense
        val currencyType = ExpenseDetailFragmentArgs.fromBundle(requireArguments()).selectedCurrencyType
        val viewModelFactory = ExpenseDetailViewModelFactory(dataSource, application, expense, currencyType)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ExpenseDetailViewModel::class.java)

        binding.imageButtonBack.setOnClickListener {
            viewModel.navigateBackToHome()
        }

        binding.imageViewIcon.setImageResource(when(expense.type) {
            1 -> R.drawable.ic_bill
            2 -> R.drawable.ic_home
            else -> R.drawable.ic_shopping_cart
        })

        binding.textViewDescription.text = expense.description

        viewModel.cost.observe(viewLifecycleOwner, {
            binding.textViewCost.text = it
        })

        binding.buttonDelete.setOnClickListener {
            viewModel.delete()
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            if(it == true)
            {
                this.findNavController().navigate(ExpenseDetailFragmentDirections.actionExpenseDetailFragmentToHomeFragment(currencyType))
                viewModel.navigationDone()
            }
        })

        return binding.root
    }
}