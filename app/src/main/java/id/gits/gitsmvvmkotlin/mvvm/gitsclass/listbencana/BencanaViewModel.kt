package id.gits.gitsmvvmkotlin.mvvm.gitsclass.listbencana;

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.util.SingleLiveEvent


class BencanaViewModel(context: Application, gitsRepository: GitsRepository) : AndroidViewModel(context) {

    val ListBencana: ObservableList<BencanaModel> = ObservableArrayList()

    // TODO if template have an error, please reimport SingleLiveEvent
    val eventClickItem = SingleLiveEvent<BencanaModel>()

    fun start() {
        loadData()
    }


    fun loadData() {
        ListBencana.clear()
        ListBencana.add(BencanaModel(1,"Gunung Meletus", "Yogyakarta, ID", "22 Des 2018", "Status Awas"))
        ListBencana.add(BencanaModel(2,"Banjir", "Jakarta, ID", "2 Mei 2018", "Status Siaga"))
        ListBencana.add(BencanaModel(3,"Gempa", "Donggala, ID", "17 Sep 2018", "Status Waspada"))
    }

}

