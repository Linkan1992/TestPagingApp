package a740362.linkan.testpagingapp.ui.adapter.trending

import a740362.linkan.testpagingapp.base.BaseRecyclerViewAdapter
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import a740362.linkan.testpagingapp.data.network.base.Result
import a740362.linkan.testpagingapp.databinding.TrendingEmaiLayoutBinding
import a740362.linkan.testpagingapp.util.AppConstants
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TrendingUserAdapter(data: MutableList<User?>) :
    BaseRecyclerViewAdapter<User, TrendingUserAdapter.TrendingUserViewHolder>(data) {

    private val visibleThreshold: Int = AppConstants.ITEM_COUNT_PER_PAGE / 2

    private var lastVisibleItem = 0

    private var totalItemCount = 0

    var offset = -1

    var pageNum = 1

    private val loadMoreLiveData by lazy { MutableLiveData<Result<TrendingEmailUser>>() }

    val mLoadMoreLiveData: LiveData<Result<TrendingEmailUser>>
        get() = loadMoreLiveData


    fun setRecyclerView(recyclerView: RecyclerView) {

        if (recyclerView.layoutManager is LinearLayoutManager) {

            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                    /**
                     * <P>Since ITEM_COUNT_PER_PAGE is "5" after first loading the recyclerView does
                     * not remain SCROLLABLE<P>
                     * totalItemCount > 0 for  paginating at first loading of recyclerView
                     * totalItemCount > 1 for  paginating at user scroll of recyclerView
                     */

                    if (!loading && totalItemCount > 1 && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loadMoreLiveData.postValue(Result.Paging())
                        loading = true
                    }

                }

            })

        }

    }

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingUserViewHolder {
        return TrendingUserViewHolder(
            TrendingEmaiLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class TrendingUserViewHolder(private val mBinding: TrendingEmaiLayoutBinding) :
        BaseRecyclerViewAdapter<User, TrendingUserViewHolder>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: User?) {

            val userViewModel = TrendingUserViewModel(model)

            mBinding.viewModel = userViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()

        }

        override fun viewDetachedFromWindow() {

        }

    }


    override fun areItemMatch(oldItem: User?, newItem: User?): Boolean {
        return oldItem?.id == newItem?.id
    }


    override fun areContentMatch(oldItem: User?, newItem: User?): Boolean {
        return oldItem?.firstName == newItem?.firstName
                && oldItem?.lastName == newItem?.lastName
                && oldItem?.email == newItem?.email
                && oldItem?.avatarUrl == newItem?.avatarUrl
    }


}