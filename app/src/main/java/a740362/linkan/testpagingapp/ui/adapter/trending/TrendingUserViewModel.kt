package a740362.linkan.testpagingapp.ui.adapter.trending

import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class TrendingUserViewModel(private  val user : User?) {

    val userId: ObservableInt

    val fullName: ObservableField<String>

    val avatar: ObservableField<String>

    val emailAddress: ObservableField<String>


    init {

        userId = ObservableInt(user?.id ?: 0)

        fullName = ObservableField((user?.firstName ?: "") + "  " + (user?.lastName ?: ""))

        avatar = ObservableField(user?.avatarUrl ?: "")

        emailAddress = ObservableField(user?.email ?: "")
    }

}