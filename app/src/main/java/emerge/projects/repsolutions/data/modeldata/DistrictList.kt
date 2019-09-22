package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

class DistrictList (
    @SerializedName("districtID")
    var districtID: Int = 0,
    @SerializedName("districtName")
    var districtName: String = ""
){}