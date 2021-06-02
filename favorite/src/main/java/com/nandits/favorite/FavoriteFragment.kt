package com.nandits.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandits.core.data.Resource
import com.nandits.core.ui.GameAdapter
import com.nandits.favorite.databinding.FragmentFavoriteBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding?=null
    private val binding get() = _binding as FragmentFavoriteBinding
    private val mViewModel by viewModel<FavoriteViewModel>()
    private val mAdapter by inject<GameAdapter>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        if (activity != null) {
            binding.toolBar.setNavigationOnClickListener { findNavController().navigateUp() }
            with(binding.rvFav) {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter
            }
            mAdapter.onItemClick = {
                mViewModel.getDetail(it.gameId).observe(viewLifecycleOwner, {  detail ->
                    when (detail) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), detail.data?.description, Toast.LENGTH_LONG).show()
                        }
                        is Resource.Loading -> {
                        
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), resources.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
            mViewModel.getFavorite.observe(viewLifecycleOwner, {
                mAdapter.setData(it)
            })
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}