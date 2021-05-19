package com.mviarchitecture.app.ui.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mviarchitecture.app.MyApp
import com.mviarchitecture.app.R
import com.mviarchitecture.app.data.model.VehicleModel
import com.mviarchitecture.app.databinding.HomeFragmentBinding
import com.mviarchitecture.app.ui.home.HomeVM
import com.mviarchitecture.app.ui.home.intent.UserIntent
import com.mviarchitecture.app.ui.home.state.UserStates
import com.mviarchitecture.app.utils.Constants.RC_PERMISSION_Location
import com.mviarchitecture.app.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeVM by viewModels()

    private val adapter = VehiclesAdapter().also {
        it.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val model = adapter.data[position] as VehicleModel
                when (view?.id) {
                    R.id.container -> {
                        snackBar(model.key, binding.root)
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        getData()
        observeViewModel()
        viewsClicks()

    }

    private fun viewsClicks() {
        binding.toolbar.filterLocationImg.setOnClickListener { checkPermissions() }
    }

    private fun setupRV() {
        binding.recyclerView.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.recyclerView.setHasFixedSize(true)
    }


    private fun getData() {
        lifecycleScope.launch {
            viewModel.userIntent.send(UserIntent.FetchUser)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is UserStates.Idle -> {

                    }
                    is UserStates.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    is UserStates.Success -> {
                        binding.progressBar.visibility = View.GONE
                        renderList(it.user)
                    }
                    is UserStates.Error -> {
                        binding.progressBar.visibility = View.GONE
                        snackBar(resources.getString(R.string.error), binding.root)
                    }
                    is UserStates.Empty -> {
                        binding.progressBar.visibility = View.GONE
                        snackBar(resources.getString(R.string.no_data), binding.root)

                    }
                    is UserStates.NoConnection -> {
                        binding.progressBar.visibility = View.GONE
                        snackBar(resources.getString(R.string.no_connection), binding.root)

                    }
                }
            }
        }
    }


    private fun renderList(users: List<VehicleModel>) {
        binding.recyclerView.visibility = View.VISIBLE
        adapter.replaceData(users)
        adapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        viewModel.clearLocation()
        getData()
        binding.swipeRefresh.isRefreshing = false
    }

    @AfterPermissionGranted(RC_PERMISSION_Location)
    private fun checkPermissions() {
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(requireActivity(), *perms)) {
            getLocation()
        } else {
            EasyPermissions.requestPermissions(
                this, resources.getString(R.string.permissions_needed),
                RC_PERMISSION_Location, *perms
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManger =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = locationManger.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        viewModel.lng = location?.longitude
        viewModel.lat = location?.latitude
        getData()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
