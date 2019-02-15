package id.gits.gitsmvvmkotlin.mvvm.gitsclass.listbencana

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast;
import android.arch.lifecycle.Observer
import id.gits.gitsmvvmkotlin.databinding.FragmentListBencanaBinding


class BencanaFragment : Fragment(), BencanaUserActionListener {
    override fun onClickItem(data: BencanaModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var mViewDataBinding: FragmentListBencanaBinding
    lateinit var mViewModel: BencanaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mViewDataBinding = FragmentListBencanaBinding.inflate(inflater, container!!, false)
        mViewModel = (activity as BencanaActivity).obtainViewModel()

        mViewDataBinding.mViewModel = mViewModel.apply {
            eventClickItem.observe(this@BencanaFragment, Observer {
                if (it != null) {
                    onClickItem(it)
                }
            })
        }

        mViewDataBinding.mListener = this@BencanaFragment

        return mViewDataBinding.root

    }





    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListData()
        mViewDataBinding.mViewModel?.start()
    }


    fun setupListData() {
        mViewDataBinding.recyclerListBencana.adapter = BencanaAdapter(mViewModel.ListBencana, mViewModel)
        mViewDataBinding.recyclerListBencana.layoutManager = LinearLayoutManager(context)
    }


    companion object {
        fun newInstance() = BencanaFragment().apply {

        }

    }

}
