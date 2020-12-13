package com.mviarchitecture.app.ui.users.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mviarchitecture.app.R
import com.mviarchitecture.app.data.model.User
import com.mviarchitecture.app.ui.adapters.UsersAdapter
import com.mviarchitecture.app.ui.users.intent.UserIntent
import com.mviarchitecture.app.ui.users.state.UserStates
import com.mviarchitecture.app.ui.users.viewmodel.UserViewModel
import com.mviarchitecture.app.utils.snackBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val userViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private val adapter = UsersAdapter().also {
        it.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val model = adapter.data[position] as User
                when (view?.id) {
                    R.id.container -> {
                        snackBar(model.name, root)
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRV()
        setupClicks()
        observeViewModel()
    }


    private fun setupRV() {
        recyclerView.adapter = adapter
    }


    private fun setupClicks() {
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                userViewModel.userIntent.send(UserIntent.FetchUser)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            userViewModel.state.collect {
                when (it) {
                    is UserStates.Idle -> {

                    }
                    is UserStates.Loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is UserStates.Success -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is UserStates.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        snackBar(resources.getString(R.string.error), root)
                    }
                    is UserStates.Empty -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        snackBar(resources.getString(R.string.no_data), root)

                    }
                    is UserStates.NoConnection -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        snackBar(resources.getString(R.string.no_connection), root)

                    }
                }
            }
        }
    }


    private fun renderList(users: List<User>) {
        recyclerView.visibility = View.VISIBLE
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}