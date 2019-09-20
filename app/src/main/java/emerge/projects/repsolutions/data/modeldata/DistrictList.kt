package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

class DistrictList (
    @SerializedName("id")
    var districtID: Int = 0,
    @SerializedName("name")
    var districtName: String = ""
){}