package id.gits.gitsmvvmkotlin.mvvm.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.gits.gitsdriver.utils.GitsBindableAdapter
import id.gits.gitsmvvmkotlin.BR
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.databinding.MainItemBinding

/**
 * Created by irfanirawansukirman on 26/01/18.
 */
class MainAdapter(private var mainViewModel: MainViewModel) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), GitsBindableAdapter<Movie> {

    var data = emptyList<Movie>()

    override fun setRecyclerViewData(data: List<Movie>) {
        this.data = data

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        val userActionListener = object : MainItemUserActionListener {
            override fun onMovieClicked(movie: Movie) {
                mainViewModel.openDetailMovie.value = movie
            }
        }

        (holder as MainItemHolder).bindItem(item, userActionListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainItemHolder(MainItemBinding.inflate(LayoutInflater
                .from(parent.context), parent, false))
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as MainItemHolder).apply {
            mainItemBinding.setVariable(BR.item, null)
            mainItemBinding.setVariable(BR.userActionListener, null)
            mainItemBinding.executePendingBindings()
        }

        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int = data.size

    class MainItemHolder(val mainItemBinding: MainItemBinding) :
            RecyclerView.ViewHolder(mainItemBinding.root) {

        fun bindItem(movie: Movie, userActionListener: MainItemUserActionListener) {
            mainItemBinding.apply {
                setVariable(BR.item, movie)
                setVariable(BR.userActionListener, userActionListener)
                executePendingBindings()
            }
        }
    }
}