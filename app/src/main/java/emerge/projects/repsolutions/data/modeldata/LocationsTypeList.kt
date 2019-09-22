package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class LocationsTypeList(
    @SerializedName("id")
    var locationsTypeID: Int = 0,
    @SerializedName("name")
    var locationsTypeName: String = ""

) {}