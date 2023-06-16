package com.developer.assignment.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.developer.assignment.databinding.FragmentDashboardBinding
import com.developer.assignment.models.Country
import com.developer.assignment.viewmodel.MainViewModel


class DashboardFragment() : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    lateinit var mainViewModel: MainViewModel
     var country :Country? = null
    constructor(objects: Array<Any>) : this() {
        if (objects.size > 0) {
            country = objects.get(0) as Country

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setListeners()
        initViewModel()


    }

    private fun initView() {
        if (country!= null){
            // set selected country to view here
            Toast.makeText(requireActivity(),"${country?.countryName}",Toast.LENGTH_SHORT).show()

        }
    }

    private fun setListeners() {

        binding.searchBar.setOnClickListener {
            mainViewModel.setOpenSelectCountriesFragment(arrayOf(1,2))
        }
       // handle  this click on edit option 
        binding.tvEdit.setOnClickListener {
            mainViewModel.setOpenSelectCountriesFragment(arrayOf(SelectCountryFragment.call_Type_Auto_Delete))
        }
    }


    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    companion object {


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DashboardFragment()
        fun newInstance(objects: Array<Any>) =
            DashboardFragment(objects)

    }
}