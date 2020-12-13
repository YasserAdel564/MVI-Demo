package com.mviarchitecture.app.ui.adapters

import com.bumptech.glide.Glide
import com.mviarchitecture.app.R
import com.mviarchitecture.app.data.model.User
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class UsersAdapter:BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_layout) {

    override fun convert(helper: BaseViewHolder, item: User) {

        Glide.with(mContext).load(item.avatar)
            .into(helper.getView(R.id.imageViewAvatar))
        helper.setText(R.id.textViewUserName, item.name)
        helper.setText(R.id.textViewUserEmail, item.email)
        helper.addOnClickListener(R.id.container)

    }
}