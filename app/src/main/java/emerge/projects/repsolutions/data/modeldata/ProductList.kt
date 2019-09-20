package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class ProductList(

    @SerializedName("productID")
    var productID: Int = 0,

    @SerializedName("productName")
    var productName: String = "",

    @SerializedName("productCode")
    var productCode: String = "",

    @SerializedName("imageUrl")
    var imageUrl: String = "",

    @SerializedName("principle")
    var principle: String = "",


    var isSelect: Boolean = false






) {}