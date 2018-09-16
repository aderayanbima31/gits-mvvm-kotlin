package id.co.gits.gitsdriver.utils

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.databinding.BindingAdapter
import android.support.v4.text.PrecomputedTextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.util.horizontalListStyle
import id.gits.gitsmvvmkotlin.util.verticalListStyle

object GitsBindings {

    @BindingAdapter("app:progressColor")
    @JvmStatic
    fun setProgressColor(loader: ProgressBar?, color: Int) {
        loader?.indeterminateDrawable?.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
    }

    @BindingAdapter("app:activeColor")
    @JvmStatic
    fun setBackgroundColorItemList(view: View, activeColor: Int?) {
        view.setBackgroundColor(activeColor ?: 0)
    }

    @BindingAdapter("app:currencyValue")
    @JvmStatic
    fun setCurrenyFormatToRupiah(textView: TextView, currencyValue: Double?) {
        textView.text = GitsHelper.Func.currencyFormatToRupiah(currencyValue
                ?: GitsHelper.Const.CURRENCY_VALUE_DEFAULT)
    }

    @SuppressLint("PrivateResource")
    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, imageUrl: String?) {
        Log.d("LOREM ", imageUrl.toString())
        GlideApp.with(view.context)
                .load(GitsHelper.Const.BASE_IMAGE_URL_MOVIE_DB + imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(
                        GitsHelper.Const.GLIDE_FADE_ANIMATION_TIME_DEFAULT
                ))
                .into(view)
                .apply {
                    RequestOptions()
                            .fallback(R.color.material_grey_300)
                }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter("app:webviewContent", "app:webviewTextSize")
    @JvmStatic
    fun setClearWebviewContent(webView: WebView, webviewContent: String?, webviewTextSize: Int?) {
        if (webviewContent != null) {
            webView.loadDataWithBaseURL(null, GitsHelper.Func
                    .setClearWebviewContent(webviewContent), "text/html",
                    "utf-8", null)
            webView.settings.javaScriptEnabled = true
            webView.settings.defaultFontSize = if (webviewTextSize == null || webviewTextSize == 0) {
                GitsHelper.Const.WEBVIEW_TEXT_SIZE_DEFAULT
            } else {
                webviewTextSize
            }
        }
    }

    @BindingAdapter("app:recyclerData", "app:orientationList")
    @JvmStatic
    fun <T> setupRecyclerviewDatas(recyclerView: RecyclerView, recyclerData: MutableLiveData<List<T>>,
                                   orientationList: Int?) {
        try {
            if (recyclerView.adapter is GitsBindableAdapter<*>) {
                if (orientationList == 1) recyclerView.horizontalListStyle() else
                    recyclerView.verticalListStyle()
                (recyclerView.adapter as GitsBindableAdapter<T>)
                        .setRecyclerViewData(recyclerData.value!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @BindingAdapter("app:textHtmlContent")
    @JvmStatic
    fun setHtmlTextContent(textView: TextView, text: String?) {
        if (text != null) {
            // textView.text = Jsoup.parse(text).text()
        }
    }

    @BindingAdapter("app:textAsync", "app:textSizes", requireAll = false)
    @JvmStatic
    fun setTextAsync(textView: TextView, textAsync: String?, textSizes: Int?) {
        if (textSizes != null) {
            textView.textSize = 14.toFloat()
        }

        val params = TextViewCompat.getTextMetricsParams(textView)
        (textView as AppCompatTextView).setTextFuture(
                PrecomputedTextCompat.getTextFuture(textAsync.toString(), params, null))
    }
}