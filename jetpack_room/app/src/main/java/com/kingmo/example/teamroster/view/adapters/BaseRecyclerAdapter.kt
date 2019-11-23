package com.kingmo.example.teamroster.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter(
    protected var itemViewModelList: List<AdapterItemViewModel>,
    private val itemClickListener: ItemClickListener?
) : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return BaseViewHolder(
            viewBinding,
            itemClickListener
        )
    }

    override fun getItemCount(): Int {
        return itemViewModelList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getLayoutResId()
    }

    private fun getItem(position: Int): AdapterItemViewModel {
        return itemViewModelList.get(position)
    }

    open class BaseViewHolder(
        private val itemBinding: ViewDataBinding,
        private val clickListener: ItemClickListener?
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        open fun bind(itemViewModel: AdapterItemViewModel) {
            itemBinding.setVariable(itemViewModel.getBindingVariable(), itemViewModel)
            itemBinding.executePendingBindings()

            itemBinding.root.setOnClickListener { clickListener?.doItemAction(itemViewModel) }
        }
    }
}

interface ItemClickListener {
    fun doItemAction(itemViewModel: AdapterItemViewModel)
    fun removeItem(itemViewModel: AdapterItemViewModel)
}