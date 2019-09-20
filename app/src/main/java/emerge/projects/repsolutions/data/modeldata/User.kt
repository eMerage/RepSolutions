package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id")
    var userID: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("userType")
    var userTypeId: Int = 0,

    @SerializedName("email")
    var email: String = "",

    @SerializedName("imageUrl")
    var imageUrl: String = "",

    @SerializedName("status")
    var userStatus: Boolean = false,

    @SerializedName("error")
    var loginNetworkError: NetworkError = NetworkError() ,

    var isUserSelected: Boolean = false

){}