package a740362.linkan.testpagingapp.base


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import a740362.linkan.testpagingapp.data.network.base.Result
import a740362.linkan.testpagingapp.databinding.ConnectionErrorRowLayoutBinding
import a740362.linkan.testpagingapp.databinding.PagingProgressLoaderBinding
import androidx.recyclerview.widget.DiffUtil

abstract class BaseRecyclerViewAdapter<T, K : BaseRecyclerViewAdapter<T, K>.BaseViewHolder>(private val data: MutableList<T?>) :
    RecyclerView.Adapter<K>() {

    // Allows to remember the last item shown on screen
    protected var lastPosition = -1

    var loading = false

    companion object {

        private val VIEW_TYPE_EMPTY = 0

        private val VIEW_TYPE_NORMAL = 1

        private val VIEW_TYPE_PAGING = 2
    }


    private var errorViewModel: EmptyErrorViewModel = EmptyErrorViewModel()

    var baseRetryLiveData: LiveData<Result<String>> = errorViewModel.emptyErrorLiveData


    protected abstract fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): K

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        when (viewType) {

            VIEW_TYPE_NORMAL ->

                return mOnCreateViewHolder(parent, viewType)

            VIEW_TYPE_PAGING -> {

                val pagingLoaderBinding = PagingProgressLoaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return PagingViewHolder(pagingLoaderBinding) as K
            }

            VIEW_TYPE_EMPTY -> {

                val errorRowLayoutBinding = ConnectionErrorRowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ConnectionErrorViewHolder(errorRowLayoutBinding) as K
            }

            else -> return mOnCreateViewHolder(parent, viewType)
        }
    }


    override fun onBindViewHolder(holder: K, position: Int) {
        if (holder != null && !data.isEmpty())
            holder.onBind(data[position])
        else if (data.isEmpty())
            holder.onBind(null)

    }


    override fun getItemCount(): Int {
        return if (!data.isEmpty()) {
            data.size
        } else {
            1
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (data.isNotEmpty() && data[position] == null) {
            VIEW_TYPE_PAGING
        } else if (data.isNotEmpty()) {
            VIEW_TYPE_NORMAL
        } else
            VIEW_TYPE_EMPTY
    }


    fun addItems(repoList: List<T?>) {
        data.addAll(repoList)
        notifyDataSetChanged()
    }


    fun clearItems() {
        data.clear()
    }

    fun getData(): List<T?> {
        return data
    }


    fun refreshData(repoList: List<T?>) {
        val oldList = data
        data.addAll(repoList)
        notifyChanges(oldList, data)
    }


    fun notifyChanges(oldList: List<T?>, newList: List<T?>) {

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemMatch(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentMatch(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size

        })

        diff.dispatchUpdatesTo(this)
    }

    abstract fun areContentMatch(oldItem: T?, newItem: T?): Boolean

    abstract fun areItemMatch(oldItem: T?, newItem: T?): Boolean


    /**
     * Add more data to recyclerView on pagination
     */
    fun addMoreData(newData: List<T>, hasDataFlag: Boolean) {

        if (hasDataFlag) if (removeData()) {
            /*  val start = itemCount
              data.addAll(newData)
              notifyItemRangeChanged(start, itemCount)*/
            refreshData(newData)
        } else removeData()

    }


    /**
     * on response received
     * removing null item to hide paging progress loader
     */
    private fun removeData(): Boolean {
        if (data[data.size - 1] == null) {
            data.removeAt(data.size - 1)
            notifyItemRemoved(data.size)
            return true
        }
        return false
    }


    /**
     * setting null item to start loader
     */
    fun setProgressItem() {
        val start = itemCount
        data.add(null)
        notifyItemRangeChanged(start, itemCount)
    }


    override fun onViewDetachedFromWindow(holder: K) {
        holder.viewDetachedFromWindow()
        super.onViewDetachedFromWindow(holder)
    }


    fun setErrorUIVisibility(visibility: Boolean) {
        errorViewModel.setIsVisible(visibility)
    }

    // Connection ViewHolder
    private inner class ConnectionErrorViewHolder(private val mBinding: ConnectionErrorRowLayoutBinding) :
        BaseRecyclerViewAdapter<T, K>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: T?) {


            if (data?.size != 0)
                errorViewModel.setIsVisible(false)

            mBinding.viewModel = errorViewModel

            mBinding.executePendingBindings()

        }

        override fun viewDetachedFromWindow() {

        }

    }


    // paging ViewHolder
    private inner class PagingViewHolder(private val mBinding: PagingProgressLoaderBinding) :
        BaseRecyclerViewAdapter<T, K>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: T?) {

            val pagingViewModel = PagingViewModel()

            pagingViewModel.setIsVisible(loading)

            if (data.size > 0 && data.get(data.size - 1) != null)
                pagingViewModel.setIsVisible(false)

            mBinding.viewModel = pagingViewModel
        }

        override fun viewDetachedFromWindow() {

        }

    }


    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun onBind(model: T?)

        abstract fun viewDetachedFromWindow()

    }

}