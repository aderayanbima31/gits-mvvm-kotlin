package id.gits.gitsmvvmkotlin.base

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.Window

import id.gits.gitsmvvmkotlin.R

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

open class BaseFragment : Fragment() {

    private var mDialogProgress: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupProgressDialog()
    }

    /**
     * Do setup progress dialog for init
     */
    private fun setupProgressDialog() {

        mDialogProgress = Dialog(activity)
        mDialogProgress?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialogProgress?.setContentView(R.layout.progress_dialog)
        mDialogProgress?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     * Show this dialog when the app request to API.
     * Hide it when the opposite
     * @param mMessage
     */
    fun showProgressDialog(isShowDialog: Boolean) {

        if (isShowDialog) {
            mDialogProgress?.show()
        } else {
            mDialogProgress?.dismiss()
        }
    }
}