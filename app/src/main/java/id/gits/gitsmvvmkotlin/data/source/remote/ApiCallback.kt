package id.gits.gitsmvvmkotlin.data.source.remote

import com.google.gson.Gson
import id.gits.gitsmvvmkotlin.base.BaseApiModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

abstract class ApiCallback<M> : Observer<M> {

    abstract fun onSuccess(model: M)

    abstract fun onFailure(code: Int, errorMessage: String)

    abstract fun onFinish()

    override fun onComplete() {
        onFinish()
    }

    override fun onNext(t: M) {
        onSuccess(t)
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        when (e) {
            is HttpException -> {
                val httpException = e
                //httpException.response().errorBody().string()
                val code = httpException.code()
                var msg = httpException.message()
                var baseDao: BaseApiModel<M>? = null
                try {
                    val body = httpException.response().errorBody()
                    baseDao = Gson().fromJson<BaseApiModel<M>>(body!!.string(), BaseApiModel::class.java)
                } catch (exception: Exception) {
                    onFailure(code, exception.message!!)
                }

                when (code) {

                    504 -> {
                        msg = baseDao?.message ?: "Error Response"
                    }
                    502, 404 -> {
                        msg = baseDao?.message ?: "Error Connect or Resource Not Found"
                    }
                    400 -> {
                        msg = baseDao?.message ?: "Bad Request"
                    }
                    401 -> {
                        msg = baseDao?.message ?: "Not Authorized"
                    }
                }

                if (baseDao != null) {
//                    msg = baseDao.message ?: ""
                }

                onFailure(code, msg)

            }
            is UnknownHostException -> onFailure(-1, "Telah terjadi kesalahan ketika koneksi ke server: ${e.message}")
            is SocketTimeoutException -> onFailure(-1, "Telah terjadi kesalahan ketika koneksi ke server: ${e.message}")
            else -> onFailure(-1, e.message ?: "Unknown error occured")
        }
        onFinish()
    }
}