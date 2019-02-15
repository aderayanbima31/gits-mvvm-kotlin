package id.gits.gitsmvvmkotlin.mvvm.gitsclass.listbencana

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView

object BencanaBindings {

        @BindingAdapter("app:listDataBencana")

        @JvmStatic

        fun setListDataBencana(recyclerView: RecyclerView, data: List<BencanaModel>) {

            with(recyclerView.adapter as BencanaAdapter) {

                replaceData(data)

            }

        }

}