package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class Area (

    @SerializedName("status")
    var areaStatus: Boolean = false,

    @SerializedName("error")
    var areaNetworkError: NetworkError = NetworkError(),

    @SerializedName("areaList")
    var areaList: ArrayList<AreaList> = ArrayList<AreaList>()


)
{}