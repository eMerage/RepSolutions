package emerge.projects.repsolutions.ui.location.newlocation.adaptar

import android.R
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import emerge.projects.repsolutions.data.modeldata.DistrictList
import kotlinx.android.synthetic.main.activity_location_new.*

object LocationBindingAdaptar {

    @BindingAdapter("districtList")
    @JvmStatic
    fun bindDistrict(view: AutoCompleteTextView, amount: ArrayAdapter<String>) {
        view.setAdapter(amount)
    }




}