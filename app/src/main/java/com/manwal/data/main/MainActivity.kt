package com.manwal.data.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manwal.R
import com.manwal.data.base.BaseActivity
import com.manwal.model.Movie
import com.manwal.network.RestClient
import kotlinx.android.synthetic.main.row_items.view.*

class MainActivity : BaseActivity<MainViewModel>() {

    private var myAdapter: MyAdapter? = null
    private var progressBar: ProgressBar? = null
    private var layoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getMovies()?.observe(this, MovieObserver())
        setData()
    }

    private fun setData() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = ProgressBar(this)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager

        viewModel.loadMoviesNetwork()
    }

    private inner class MovieObserver : Observer<List<Movie>?> {
        override fun onChanged(movies: List<Movie>?) {
            if (movies == null) return
            myAdapter = MyAdapter()
            myAdapter?.setItems(movies)
            recyclerView?.adapter = myAdapter
        }
    }

    class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private var items: List<Movie> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val imageUrl = items.get(position).image!!
            val title = items.get(position).title!!
            val desc = items.get(position).description!!

            holder.itemView.tvTitle.text = title
            holder.itemView.tvDescription.text = desc
            Glide.with(holder.itemView.context).load(imageUrl)
                .into(holder.itemView.imageView)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        fun setItems(items: List<Movie>) {
            this.items = items
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

    override fun createViewModel(): MainViewModel {
        val factory = MainViewModelFactory(RestClient.instance!!)
        return ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

}
