package emerge.projects.repsolutions.ui.location.newlocation.adaptar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.DistrictList
import emerge.projects.repsolutions.data.modeldata.LocationsTypeList
import emerge.projects.repsolutions.data.modeldata.TownList
import kotlinx.android.synthetic.main.textview_autocomplete.view.*

class SpinnerLocationTypeAdaptor(
    context: Context, @LayoutRes private val layoutResource: Int,
    private val list: ArrayList<LocationsTypeList>
) :
    ArrayAdapter<LocationsTypeList>(context, layoutResource, list) {

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(context).inflate(layoutResource, parent, false)
        view.lbl_name.text = "${list[position].locationsTypeName}"
        return view
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(context).inflate(layoutResource, parent, false)

        view.lbl_name.text = "${list[position].locationsTypeName}"

        return super.getDropDownView(position, view, parent)
    }


}

