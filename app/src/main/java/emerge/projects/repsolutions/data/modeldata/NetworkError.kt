package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

class NetworkError(

    var code: Int = 0,

    @SerializedName("code")
    var errorCode: String? = null,

    @SerializedName("description")
    var errorMessage: String? = null,

    @SerializedName("type")
    var errorTitle: String? = null )  {
}