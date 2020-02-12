package a740362.linkan.testpagingapp.base

import androidx.databinding.ObservableBoolean

class PagingViewModel {

    val isPagingVisible = ObservableBoolean(false)


    fun setIsVisible(value: Boolean) {
        this.isPagingVisible.set(value)
    }

}