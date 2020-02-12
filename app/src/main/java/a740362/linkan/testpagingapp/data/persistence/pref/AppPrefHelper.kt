package a740362.linkan.testpagingapp.data.persistence.pref

import android.content.SharedPreferences
import a740362.linkan.testpagingapp.di.annotation.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AppPrefHelper @Inject
constructor(private val sharedPref : SharedPreferences, @CoroutineScopeIO private val ioCoroutineScope : CoroutineScope): PrefHelper {




}