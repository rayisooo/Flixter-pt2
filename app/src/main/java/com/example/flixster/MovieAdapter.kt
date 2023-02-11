package com.example.flixster

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MovieAdapter"
 const val MOVIE_EXTRA = "MOVIE_EXTRA"

class MovieAdapter(private val context : Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG,"ONCREATEVIEWHOLDER")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.TvOverview)
        private val tvimage = itemView.findViewById<ImageView>(R.id.imageView)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(movie : Movie){
            tvOverview.text = movie.overview
            tvTitle.text =  movie.title
            //image
            Glide.with(context).load(movie.posterImageUrl).into(tvimage)
        }

        override fun onClick(p0: View?) {
            val movie = movies[adapterPosition]
            Toast.makeText(context , movie.title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,Detailactivity::class.java)


            intent.putExtra("MOVIE_EXTRA",movie)

            context.startActivity(intent)
        }

    }

}
