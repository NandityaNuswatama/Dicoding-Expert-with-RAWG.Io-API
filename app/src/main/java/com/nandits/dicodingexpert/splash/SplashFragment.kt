package com.nandits.dicodingexpert.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nandits.dicodingexpert.R
import com.nandits.dicodingexpert.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding as FragmentSplashBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val runnable = Runnable {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 1500L)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}