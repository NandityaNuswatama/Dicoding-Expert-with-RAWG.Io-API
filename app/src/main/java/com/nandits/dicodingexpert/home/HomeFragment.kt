package com.nandits.dicodingexpert.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandits.core.data.Resource
import com.nandits.core.ui.GameAdapter
import com.nandits.dicodingexpert.R
import com.nandits.dicodingexpert.databinding.FragmentHomeBinding
import com.nandits.dicodingexpert.detail.DetailFragment.Companion.GAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private val mAdapter by inject<GameAdapter>()
    private val mViewModel by viewModel<HomeViewModel>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        if (activity != null){
            
            activity?.onBackPressedDispatcher?.addCallback(onBackCallback)
            binding.fabHomFav.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
            }
            findNavController().addOnDestinationChangedListener { _, destination, _ ->
                onBackCallback.isEnabled = destination.id == R.id.homeFragment
            }
            
            with(binding.rvHome){
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter
            }
            mAdapter.onItemClick = {
                mViewModel.getDetail(it.gameId).observe(viewLifecycleOwner, {  detail ->
                    when (detail) {
                        is Resource.Success -> {
                            binding.shimmerDetail.isGone = true
                            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundleOf(GAME to it))
                        }
                        is Resource.Loading -> {
                            binding.rvHome.isGone = true
                            binding.shimmerDetail.isGone = false
                            binding.fabHomFav.isGone = true
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), resources.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
            
            mViewModel.game.observe(viewLifecycleOwner, {
                if (it != null){
                    when(it){
                        is Resource.Loading -> {
                            binding.shimmer.isShimmerVisible
                            binding.fabHomFav.isGone = true
                        }
                        is Resource.Success -> {
                            mAdapter.setData(it.data)
                            binding.shimmer.isGone = true
                            binding.fabHomFav.isGone = false
                        }
                        is Resource.Error -> {
                            binding.shimmer.isGone = true
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                            Toast.makeText(requireContext(), resources.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }
    
    private var doubleBackToExitPressedOnce = false
    private val onBackCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (doubleBackToExitPressedOnce) {
                exitApp()
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(requireActivity(), resources.getString(R.string.press_back_again), Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000L)
                changeListener().collect { doubleBackToExitPressedOnce = !it }
            }
        }
    }
    
    private fun changeListener(): Flow<Boolean>{
        val bool = mutableListOf(true, false)
        return bool.asFlow()
    }
    
    private fun exitApp(){
        requireActivity().finish()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}