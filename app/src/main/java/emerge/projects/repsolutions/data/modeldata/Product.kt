package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("status")
    var productStatus: Boolean = false,

    @SerializedName("error")
    var productNetworkError: NetworkError = NetworkError(),

    @SerializedName("productList")
    var productList: ArrayList<ProductList> = ArrayList<ProductList>()

){}