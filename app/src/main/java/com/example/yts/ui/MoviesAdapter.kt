package com.example.yts.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yts.data.model.Movie
import com.example.yts.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MoviesAdapter (private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val movies = mutableListOf<Movie>()

    fun addMoviesToList(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearMovies(){
        movies.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MoviesViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> {
                holder.bind(movies[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MoviesViewHolder(private val binding: ItemMovieBinding, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Movie) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            Picasso.get().load(item.large_cover_image)
                .into(binding.movieImage)
            binding.movieTitle.text = item.title
            binding.movieYear.text = item.year.toString()
            binding.movieCategory.text = item.genres[0]
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Movie)
    }
}
