package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

class TownList (
    @SerializedName("id")
    var townID: Int = 0,
    @SerializedName("name")
    var townName: String = ""
){}