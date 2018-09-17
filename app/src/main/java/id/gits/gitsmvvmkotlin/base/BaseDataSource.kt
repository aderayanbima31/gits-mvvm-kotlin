package id.gits.gitsmvvmkotlin.base

/**
 * Dibuat oleh Irfan Irawan Sukirman
 * @Copyright 2018
 */
interface BaseDataSource {
    interface GitsResponseCallback<T> {
        fun onShowProgressDialog()
        fun onHideProgressDialog()
        fun onSuccess(data: T)
        fun onFinish()
        fun onFailed(statusCode: Int, errorMessage: String? = "")
    }
}