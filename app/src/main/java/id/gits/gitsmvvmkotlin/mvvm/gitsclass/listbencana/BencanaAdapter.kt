package id.gits.gitsmvvmkotlin.mvvm.gitsclass.listbencana


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.readystatesoftware.chuck.internal.ui.MainActivity
import id.gits.gitsmvvmkotlin.databinding.ItemListBencanaBinding


class BencanaAdapter(var mData: List<BencanaModel>, val mViewModel: BencanaViewModel) : RecyclerView.Adapter<BencanaAdapter.ListBencanaItem>() {


    override fun onBindViewHolder(holder: ListBencanaItem, position: Int) {
        holder.bind(mData[position], mViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBencanaItem {
        val binding = ItemListBencanaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListBencanaItem(binding)
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    fun replaceData(data: List<BencanaModel>) {
        mData = data
        notifyDataSetChanged()
    }


    class ListBencanaItem(binding: ItemListBencanaBinding) : RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding;

        fun bind(data: BencanaModel, viewModel: BencanaViewModel) {
            mBinding.mData = data
            mBinding.mListener = object : BencanaUserActionListener {

                override fun onClickItem(data: BencanaModel) {
                    viewModel.eventClickItem.value = data
                }

            }
        }

    }

}