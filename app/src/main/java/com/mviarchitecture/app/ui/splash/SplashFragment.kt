package com.mviarchitecture.app.ui.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mviarchitecture.app.R
import com.mviarchitecture.app.databinding.SplashFragmentBinding
import com.mviarchitecture.app.utils.flashAnimation

class SplashFragment : Fragment() {

    lateinit var binding: SplashFragmentBinding
    var timer: CountDownTimer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
        goToHome()
    }

    private fun goToHome() {
        timer = object : CountDownTimer(3000, 500) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                homeDirection()
            }
        }
        timer!!.start()
    }

    private fun startAnimation() {
        activity?.flashAnimation(binding.splahIcon)

    }

    private fun homeDirection() {
        findNavController().navigate(R.id.action_splash_fragment_to_home_fragment)
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
}