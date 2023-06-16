package com.developer.assignment.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.assignment.databinding.FragmentSelectCountryBinding
import com.developer.assignment.models.Country
import com.developer.assignment.ui.adapters.AdapterCountries
import com.developer.assignment.utils.IListeners
import com.developer.assignment.utils.hideVisibility
import com.developer.assignment.utils.showVisibility
import com.developer.assignment.viewmodel.MainViewModel


class SelectCountryFragment() : Fragment() {

    lateinit var binding: FragmentSelectCountryBinding
    lateinit var mainViewModel: MainViewModel

    var countryList = ArrayList<Country>()
    lateinit var countriesAdapter: AdapterCountries
    var selectedCountry: Country? = null

    var callType = ""

    var counterA :Int =0
    constructor(objects: Array<Any>) : this() {
        if (objects.size > 0 && objects.get(0) is String) {

            callType = objects.get(0) as String


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
        binding = FragmentSelectCountryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setListeners()
        initViewModel()
        getData()
        initAdapter()

        if (callType.equals(call_Type_Auto_Delete)) {
            startAutoRemoveCountries(counterA)
        }

    }

    private fun startAutoRemoveCountries(count: Int) {

var counter = count
        if (countryList.isEmpty()) {
            Handler(Looper.myLooper()!!).postDelayed({
                getData()
            }, 1000)

        }

        if (countryList.size > counter ) {


            Handler(Looper.myLooper()!!).postDelayed({
                selectedCountry = countryList.get(counter)

                countryList.remove(selectedCountry)
                countriesAdapter.notifyItemRemoved(0)
                startAutoRemoveCountries(counter )

            }, 10000)

        }
    }

    private fun initView() {
        binding.btnConform.hideVisibility()
    }


    private fun getData() {

        mainViewModel.getCountries().observe(this, Observer { list ->
            countryList.clear()
            countryList.addAll(list)
            if (callType.equals(call_Type_Auto_Delete)){
                startAutoRemoveCountries(0)
            }

        })
    }

    private fun setListeners() {

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchCountry(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            // Perform your refresh operations here
            if (selectedCountry != null) {
                countryList.add(0, selectedCountry!!)
                countriesAdapter.notifyItemInserted(0)
                binding.swipeRefreshLayout.isRefreshing = false

            }

            // When the refresh is complete, call setRefreshing(false) to stop the loading animation

        }

        binding.btnConform.setOnClickListener {
            // this will not happen when a user click on conform btn and country will not be selected
            // this is just for a better practice
            binding.btnConform.hideVisibility()
            if (selectedCountry != null) {
                mainViewModel.setOpenDashboardFragment(arrayOf(selectedCountry!!))
            } else {
                Toast.makeText(requireActivity(), "Select Country", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun searchCountry(text: String?) {
        var listTemp = ArrayList<Country>()

        for (d in countryList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (text?.let { d.countryName?.lowercase()?.contains(it.lowercase()) }!!) {
                listTemp.add(d)
            }
        }
        //update recyclerview
        countriesAdapter.updateList(listTemp, text?.lowercase()!!)
    }


    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun initAdapter() {
        countriesAdapter = AdapterCountries(countryList, object : IListeners.IClickListener {


            override fun onItemClicked(model: Any, pos: Int) {
                if (model is Country) {
                    model.isSelected = true
                    selectedCountry?.isSelected = false
                    selectedCountry = model
                    binding.btnConform.showVisibility()
                }
            }


        })

        binding.countriesRV.layoutManager = LinearLayoutManager(requireContext())
        binding.countriesRV.adapter = countriesAdapter
    }

    fun showConformDialog(pos: Int) {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Conform")


        builder.setPositiveButton("Yes") { dialog, which ->
            //perform action here
        }

        builder.setNegativeButton("No") { dialog, which ->

        }


        builder.show()
    }


    companion object {

        const val call_Type_Auto_Delete = "AutoDelete"

        @JvmStatic
        fun newInstance() =
            SelectCountryFragment()

        @JvmStatic
        fun newInstance(objects: Array<Any>) =
            SelectCountryFragment(objects)
    }
}