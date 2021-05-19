package com.mviarchitecture.app.ui.home.view

import com.mviarchitecture.app.R
import com.mviarchitecture.app.data.model.VehicleModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class VehiclesAdapter : BaseQuickAdapter<VehicleModel, BaseViewHolder>(R.layout.vehicle_item_view) {


    override fun convert(helper: BaseViewHolder, item: VehicleModel) {
        helper.addOnClickListener(R.id.container)
        helper.setText(R.id.commodity_txt, item.commodity)
        helper.setText(R.id.truck_type_txt, item.vehicleType)
        helper.setText(R.id.bids_txt, item.numberOfBids.toString())
        helper.setText(R.id.price_txt, item.price.toString())

        var addresses = ""
        for (i in item.addresses.indices) {
            addresses = addresses + " - " + item.addresses[i].name
        }
        helper.setText(R.id.address_txt, addresses)

    }
}