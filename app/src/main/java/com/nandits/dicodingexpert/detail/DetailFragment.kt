package com.nandits.dicodingexpert.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.nandits.core.domain.model.Game
import com.nandits.core.ui.StringAdapter
import com.nandits.dicodingexpert.R
import com.nandits.dicodingexpert.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding?= null
    private val binding get() = _binding!!
    private val mViewModel by viewModel<DetailViewModel>()
    private val mGame by lazy {
        arguments?.getParcelable<Game>(GAME)
    }
    private val genreAdapter by inject<StringAdapter>()
    private val platformAdapter by inject<StringAdapter>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener { findNavController().navigateUp() }
        
        mViewModel.getDetailGame(mGame!!.gameId).observe(viewLifecycleOwner, {
            with(binding){
                toolBar.title = it.name
                tvDesc.text = it.description
                tvRatings.text = it.rating.toString()
                imgPoster.load(it.image)
                
                rvGenre.apply {
                    adapter = genreAdapter
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
                }
                genreAdapter.setData(it.genre)
                
                rvPlatform.apply {
                    adapter = platformAdapter
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
                }
                platformAdapter.setData(it.platform)
                
                var statusFavorite = it.isFavorite
                isFavorite(statusFavorite)
                fabFav.setOnClickListener { _ ->
                    statusFavorite = !statusFavorite
                    mViewModel.setFavorite(it, statusFavorite)
                    isFavorite(statusFavorite)
                }
            }
        })
    }
    
    private fun isFavorite(status: Boolean){
        if (status){
            binding.fabFav.setImageResource(R.drawable.icon_fav)
        }else{
            binding.fabFav.setImageResource(R.drawable.icon_favorite_border)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object{
        const val GAME = "gameId"
    }
}