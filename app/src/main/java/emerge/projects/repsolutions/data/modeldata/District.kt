package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class District (

    @SerializedName("districtMainStatus")
    var districtStatus: Boolean = false,

    @SerializedName("error")
    var districtNetworkError: NetworkError = NetworkError(),

    @SerializedName("districtList")
    var districtList: ArrayList<DistrictList> = ArrayList<DistrictList>(),

    @SerializedName("districtCount")
    var districtCount: Int = 0

)
{}