package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class SpecializationList(
    @SerializedName("id")
    var specID: Int = 0,
    @SerializedName("name")
    var specName: String = ""
){}