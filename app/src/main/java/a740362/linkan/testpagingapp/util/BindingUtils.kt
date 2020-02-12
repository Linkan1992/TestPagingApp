package a740362.linkan.testpagingapp.util

import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import a740362.linkan.testpagingapp.ui.adapter.trending.TrendingUserAdapter
import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.drawee.view.SimpleDraweeView


@BindingAdapter("bind:imageUrl")
fun setImageUrl(draweeView: SimpleDraweeView, imageUrl: String?) {

    val uri = Uri.parse(imageUrl ?: "")
    draweeView.setImageURI(uri)
}


@BindingAdapter("pullToRefresh", "enableRefreshing")
fun bindSwipeRefreshListener(
    pullToRefresh: SwipeRefreshLayout,
    refreshListener: SwipeRefreshLayout.OnRefreshListener,
    refreshLoader: Boolean
) {

    pullToRefresh.setOnRefreshListener(refreshListener)

    pullToRefresh.isRefreshing = refreshLoader

    pullToRefresh.setColorSchemeResources(
        android.R.color.black,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )

}


@BindingAdapter("userAdapter", "hasMoreData", "isRefreshing")
fun bindUserAdapter(recyclerView: RecyclerView, userList: List<User>, hasMoreData: Boolean, isRefreshing: Boolean) {

    val adapter = recyclerView.adapter as TrendingUserAdapter?

    adapter?.let {

        if (isRefreshing) {
            it.offset = -1
            it.pageNum = 1
            it.loading = false
            it.clearItems()
            return
        }

        if (it.getData().size >= AppConstants.ITEM_COUNT_PER_PAGE) it.addMoreData(
            userList,
            hasMoreData
        ) else it.addItems(userList)

        if (hasMoreData)
            it.loading = false

        if (userList.size < AppConstants.ITEM_COUNT_PER_PAGE)
            it.loading = true
    }

}