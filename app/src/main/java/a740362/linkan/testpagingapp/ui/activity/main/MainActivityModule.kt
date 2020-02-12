package a740362.linkan.testpagingapp.ui.activity.main

import a740362.linkan.testpagingapp.ui.adapter.trending.TrendingUserAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideLinearLayoutManager(activity: MainActivity) : LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    fun provideTrendingAdapter() : TrendingUserAdapter{
        return TrendingUserAdapter(ArrayList())
    }

}