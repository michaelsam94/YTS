package com.example.yts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yts.R
import com.example.yts.data.model.Movie
import com.example.yts.databinding.FragmentMoviesBinding
import com.example.yts.utils.Resource
import com.example.yts.utils.gone
import com.example.yts.utils.show
import com.example.yts.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment(R.layout.fragment_movies), MoviesAdapter.Interaction {

    val viewModel: MoviesViewModel by viewModel()
    
    private lateinit var binding: FragmentMoviesBinding

    private val moviesAdapter by lazy { MoviesAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "YTS App"
        setupRecyclerView()
        observeToMoviesLiveData()
    }

    private fun observeToMoviesLiveData() {
        viewModel.getMovies().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    binding.progressBar.gone()
                    it.msg?.let { msg -> showToast(msg) }
                }
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    binding.progressBar.gone()
                    if (it.data != null) {
                       moviesAdapter.addMoviesToList(it.data)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.moviesRecycler.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(context,2)
        }

    }



    override fun onItemSelected(position: Int, item: Movie) {
        val action = MoviesFragmentDirections.actionNewsFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }


}