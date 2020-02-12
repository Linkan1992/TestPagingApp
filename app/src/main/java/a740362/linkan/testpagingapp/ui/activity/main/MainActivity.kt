package a740362.linkan.testpagingapp.ui.activity.main


import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import a740362.linkan.testpagingapp.BR
import a740362.linkan.testpagingapp.R
import a740362.linkan.testpagingapp.ViewModelProviderFactory
import a740362.linkan.testpagingapp.base.BaseActivity
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import a740362.linkan.testpagingapp.data.network.base.Result
import a740362.linkan.testpagingapp.databinding.ActivityMainBinding
import a740362.linkan.testpagingapp.ui.adapter.trending.TrendingUserAdapter
import a740362.linkan.testpagingapp.util.AppConstants
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


  /*  private var offset = -1

    private var pageNum = 1*/

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var userAdapter: TrendingUserAdapter


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }


    override val layoutId: Int
        get() = R.layout.activity_main


    override val viewModel: MainViewModel
        get() = mainViewModel


    override val bindingVariable: Int
        get() = BR.viewModel

    override val toolbar: Toolbar?
        get() = viewDataBinding.includedAppBar.toolbar


    override fun initOnCreate(savedInstanceState: Bundle?) {
        setStatusBarTranslucent()
        //.. To Hide the home back button
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        // call to fetch data from server or db cahe
        mainViewModel.fetchDataFromCacheOrServer(isOnline = connectionStateMonitor?.hasNetworkConnection() ?: false)

        subscribeLiveData()
        setUpRecyclerView()

        setUpRecyclerViewScroll()
    }


    private fun subscribeLiveData() {


        mainViewModel.mTrendingUserLiveData.observe(this) { result: Result<TrendingEmailUser> ->
            when (result) {
                is Result.Loading -> {
                    if (result.isRefreshed) mainViewModel.setRefreshed(true) else mainViewModel.setLoading(true)
                }
                is Result.Success -> {
                    //  showToast(result.toString())
                    mainViewModel.setUserList(result.data.dataList)
                    mainViewModel.setLoading(false)
                    mainViewModel.setRefreshed(false)

                    if (result.isNetworkCall) mainViewModel.performDbSyncing(result.data.dataList) else mainViewModel.syncDbCache =
                        true;
                }
                is Result.Error -> {
                    userAdapter.setErrorUIVisibility(true)
                    mainViewModel.setLoading(false)
                    mainViewModel.setRefreshed(false)
                }
                is Result.Paging -> {

                }
            }
        }

        userAdapter.baseRetryLiveData.observe(this) { result: Result<String> ->
            when (result) {
                is Result.Retry -> {
                    mainViewModel.fetchDataFromCacheOrServer(
                        isOnline = connectionStateMonitor?.hasNetworkConnection() ?: false
                    )
                }
            }
        }


        userAdapter.mLoadMoreLiveData.observe(this) { result: Result<TrendingEmailUser> ->
            when (result) {
                is Result.Paging -> {

                    if (userAdapter.offset == -1)
                        userAdapter.offset = 0

                    userAdapter.offset += AppConstants.ITEM_COUNT_PER_PAGE

                    userAdapter.pageNum += 1

                    mainViewModel.paginateFromCacheOrServer(
                        isOnline = connectionStateMonitor?.hasNetworkConnection() ?: false,
                        offsetId = userAdapter.offset,
                        page_num = userAdapter.pageNum
                    )

                    userAdapter.setProgressItem()
                }
            }
        }

    }


    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        viewDataBinding.userRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.userRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.userRecyclerView.adapter = userAdapter

    }


    private fun setUpRecyclerViewScroll() {
        userAdapter.setRecyclerView(viewDataBinding.userRecyclerView)
    }


    private fun setStatusBarTranslucent() {

        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }


    companion object {

        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }


}
