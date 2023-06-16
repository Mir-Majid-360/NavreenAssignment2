package com.developer.assignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.developer.assignment.R
import com.developer.assignment.databinding.ActivityMainBinding
import com.developer.assignment.ui.fragments.DashboardFragment
import com.developer.assignment.ui.fragments.SelectCountryFragment
import com.developer.assignment.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        observeViewModel()

        openFragment(DashboardFragment.newInstance(), "")    }
    private fun observeViewModel() {

        mainViewModel.getOpenDashboardFragment().observe(this) { event ->
            val objects: Array<Any>? = event.getContentIfNotHandled()
            if (objects != null) {
                openFragment(DashboardFragment.newInstance(objects), "")
            }
        }
        mainViewModel.getOpenSelectCountriesFragment().observe(this) { event ->
            val objects: Array<Any>? = event.getContentIfNotHandled()
            if (objects != null) {
                openFragment(SelectCountryFragment.newInstance(objects), "")
            }
        }

    }
    private fun openFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        setFragmentTransactionAnimation(transaction, tag)
        transaction.replace(R.id.mainContainer, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }
    private fun setFragmentTransactionAnimation(transaction: FragmentTransaction, tag: String) {
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
    }

    private fun initViewModel() {

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

}