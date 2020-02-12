package a740362.linkan.testpagingapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import a740362.linkan.testpagingapp.data.network.ApiHelper
import a740362.linkan.testpagingapp.data.persistence.db.DbHelper
import a740362.linkan.testpagingapp.data.persistence.pref.PrefHelper
import a740362.linkan.testpagingapp.di.annotation.CoroutineScopeIO
import a740362.linkan.testpagingapp.ui.activity.main.MainViewModel
import a740362.linkan.testpagingapp.ui.activity.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Linkan on 19/10/19.
 *
 *
 * A provider factory that persists ViewModels [ViewModel].
 * Used if the view model has a parameterized constructor.
 */

@Singleton
class ViewModelProviderFactory @Inject
constructor(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {

            return SplashViewModel(apiHelper, prefHelper, ioCoroutineScope) as T
        } else
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {

                return MainViewModel(dbHelper, apiHelper, prefHelper) as T
            }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name} " /*+ modelClass.name*/)
    }
}