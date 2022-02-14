package com.dott.findrestaurants.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dott.findrestaurants.R
import com.dott.findrestaurants.details.viewmodel.DetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.resto_details_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : BottomSheetDialogFragment()
{
    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.resto_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("id")?.let {
            viewModel.getRestaurantDetails(it)
        }
        setUpViewModelListeners()
    }

    private fun setUpViewModelListeners()
    {
        viewModel.restoData.observe(this, Observer {
            if (it != null) {
                name.text = String.format(getString(R.string.name), it.name)
                longlat.text = it.longLat
                address.text = String.format(getString(R.string.address), it.address)
                postcode.text = String.format(getString(R.string.postcode), it.postCode)
                Picasso.get().load(it.icon).into(resto_icon);
            }
        })
    }
}