package id.gits.gitsmvvmkotlin.mvvm.maindetail

import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import id.co.gits.gitsdriver.utils.GitsHelper
import id.co.gits.gitsdriver.utils.GlideApp
import id.gits.gitsmvvmkotlin.base.BaseFragment
import id.gits.gitsmvvmkotlin.databinding.MainDetailFragmentBinding
import id.gits.gitsmvvmkotlin.util.putArgs

class MainDetailFragment : BaseFragment() {

    private lateinit var viewBinding: MainDetailFragmentBinding
    private lateinit var viewModel: MainDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = MainDetailFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MainDetailActivity).obtainViewModel().apply {
                eventGlobalMessage.observe(this@MainDetailFragment, Observer { message ->
                    GitsHelper.View.showSnackbarDefault(viewBinding.root, message
                            ?: GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT,
                            GitsHelper.Const.SNACKBAR_TIMER_SHOWING_DEFAULT)
                })
                movieImagePosterUrl.observe(this@MainDetailFragment, Observer { imageUrl ->
                    // Sample create image file from URL n' save it into memory
                    GlideApp.with(this@MainDetailFragment)
                            .asBitmap()
                            .load(GitsHelper.Const.BASE_IMAGE_URL_MOVIE_DB + imageUrl)
                            .listener(object : RequestListener<Bitmap> {
                                override fun onLoadFailed(e: GlideException?, model: Any?,
                                                          target: Target<Bitmap>?, isFirstResource:
                                                          Boolean): Boolean {
                                    return false
                                }

                                override fun onResourceReady(resource: Bitmap?, model: Any?,
                                                             target: Target<Bitmap>?, dataSource:
                                                             DataSource?, isFirstResource: Boolean):
                                        Boolean {
                                    return if (resource != null) {
                                        GitsHelper.Func.saveBitmapToLocalFile(context!!, resource,
                                                null)
                                        true
                                    } else {
                                        false
                                    }
                                }
                            })
                            .submit()
                })
            }
        }

        viewBinding.let {
            it.viewModel = viewBinding.viewModel
            it.setLifecycleOwner(this@MainDetailFragment)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMainDetailViewModel()

        getMoviesById(arguments?.getInt(GitsHelper.Const.ARGUMENT_MOVIE_ID)
                ?: GitsHelper.Const.NUMBER_DEFAULT)
    }

    private fun setupMainDetailViewModel() {
        viewModel = viewBinding.viewModel!!
    }

    private fun getMoviesById(movieId: Int) {
        viewModel.getMovieById(movieId)
    }

    companion object {
        fun newInstance(movieId: Int) = MainDetailFragment().putArgs {
            putInt(GitsHelper.Const.ARGUMENT_MOVIE_ID, movieId)
        }
    }

}
