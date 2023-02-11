package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers
import org.w3c.dom.Text
private const val youtubeApiKey = "AIzaSyBPJ1-sZh-W-dxqNAmLE05tVILbnkSkMBU"
private const val Trailers_url = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "Detailactivity"
class Detailactivity : YouTubeBaseActivity() {

    private  lateinit var  tvOverview :TextView
    private  lateinit var  tvTitle :TextView
    private  lateinit var  ratingBar :RatingBar
    private  lateinit var  ytPlayeView :YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailactivity)
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        ratingBar = findViewById(R.id.rbVoteAverage)
        ytPlayeView = findViewById(R.id.player)

       val movie =  intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG ,"Movie is $movie")

        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        ratingBar.rating = movie.voteAverage.toFloat()

        val client = AsyncHttpClient()
        client.get(Trailers_url.format(movie.movieId),object :JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {

                Log.i(TAG,"onfail $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {

                Log.i(TAG,"onsuccess")
                val results = json.jsonObject.getJSONArray("results")
                if(results.length() == 0 ){
                    Log.w(TAG,"no trailer found")
                    return
                }
                val movieTrailerJSON = results.getJSONObject(0)
                val youtubeKey = movieTrailerJSON.getString("key")

                initializeYoutube(youtubeKey)
            }

        })



    }

    private fun initializeYoutube(youtubeKey: String) {
        ytPlayeView.initialize(youtubeApiKey,object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {

                player?.cueVideo(youtubeKey)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }

        })
    }
}