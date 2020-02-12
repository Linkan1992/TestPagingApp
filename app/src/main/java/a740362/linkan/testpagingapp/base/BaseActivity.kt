package a740362.linkan.testpagingapp.base

import a740362.linkan.testpagingapp.R
import a740362.linkan.testpagingapp.util.NetworkStateMonitor
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity(),
    NetworkStateMonitor.OnNetworkAvailableCallbacks {

    var connectionStateMonitor: NetworkStateMonitor? = null

    lateinit var viewDataBinding: T
        private set

    var mViewModel: V? = null;

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    abstract val bindingVariable: Int

    abstract val toolbar: Toolbar?

    abstract fun initOnCreate(savedInstanceState: Bundle?)


    override fun onResume() {
        super.onResume()

        if (connectionStateMonitor == null)
            connectionStateMonitor = NetworkStateMonitor(this, this)

        //Register
        connectionStateMonitor?.enable()

    }

    override fun onPause() {

        //Unregister
        connectionStateMonitor?.disable()
        connectionStateMonitor = null

        super.onPause()
    }


    override fun onConnect() {
        runOnUiThread {
            showSnackbar("online")
        }
    }

    override fun onDisconnect() {
        runOnUiThread {
            showSnackbar("offline")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()

        if (connectionStateMonitor == null)
            connectionStateMonitor = NetworkStateMonitor(this, this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initOnCreate(savedInstanceState)
        title = resources.getString(R.string.empty)

    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = mViewModel ?: viewModel
        viewDataBinding.setVariable(bindingVariable, mViewModel)
        viewDataBinding.executePendingBindings()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }


}