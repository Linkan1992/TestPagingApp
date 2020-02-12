package a740362.linkan.testpagingapp.data.entity.api.trendingEmail

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "trending_user")
data class User(

    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("email")
    @Expose
    val email: String?,

    @SerializedName("first_name")
    @Expose
    val firstName: String,

    @SerializedName("last_name")
    @Expose
    val lastName: String,

    @SerializedName("avatar")
    @Expose
    val avatarUrl : String?

) : Serializable