package a740362.linkan.testpagingapp.data.entity.api.trendingEmail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TrendingEmailUser(

    @SerializedName("page")
    @Expose
    var page: Int = 0,

    @SerializedName("per_page")
    @Expose
    var perPage: Int = 0,

    @SerializedName("total")
    @Expose
    var total: Int = 0,

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0,

    @SerializedName("data")
    @Expose
    var dataList: List<User>

) : Serializable