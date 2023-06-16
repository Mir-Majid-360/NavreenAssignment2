package com.developer.assignment.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.assignment.databinding.ItemCountryBinding
import com.developer.assignment.glide.Glide
import com.developer.assignment.models.Country
import com.developer.assignment.utils.IListeners


class AdapterCountries(var list:ArrayList<Country>, val listener: IListeners.IClickListener): RecyclerView.Adapter<AdapterCountries.MoviesViewHolder>() {



    var searchText =""
    class MoviesViewHolder(val binding:ItemCountryBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(country : Country){
            if (country.isSelected){
                // set highlighted bg
            }else{
                //set  common background
            }
            binding.tvName.text=country.countryName
            binding.tvCountryCode.text = country.phoneCode
            Glide.glideFetch(country.image!!,binding.ivFlag)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemBinding =
            ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
           holder.bind(list.get(position))
           holder.itemView.setOnClickListener {
               listener.onItemClicked(list.get(position),position)
           }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listTemp: ArrayList<Country>, text: String) {

        list = listTemp
        this.searchText = text
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}