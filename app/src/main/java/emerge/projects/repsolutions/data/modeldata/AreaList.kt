package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

class AreaList (
    @SerializedName("id")
    var areaID: Int = 0,
    @SerializedName("name")
    var areaName: String = ""
){}