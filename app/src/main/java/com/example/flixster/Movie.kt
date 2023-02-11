package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
@Parcelize
data class Movie (
    val overview : String,
    val title : String,
    val movieId : Int,
    val voteAverage : Double,
    private val posteraPath : String,
    ) : Parcelable
{
    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posteraPath"
   companion object {
       fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
           for(i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)

               movies.add(
                   Movie(
                       movieJson.getString("overview"),
                       movieJson.getString("title"),
                       movieJson.getInt("id"),
                       movieJson.getDouble("vote_average"),
                       movieJson.getString("poster_path"),

                   )

               )
           }
           return movies
       }
   }

   }
