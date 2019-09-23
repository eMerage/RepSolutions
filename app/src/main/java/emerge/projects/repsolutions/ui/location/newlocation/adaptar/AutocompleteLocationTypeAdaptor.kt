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

class SpinnerLocationTypeAdaptor(ctx: Context, moods: ArrayList<LocationsTypeList>) :
    ArrayAdapter<LocationsTypeList>(ctx, 0, moods) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val mood = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.textview_autocomplete,
            parent,
            false
        )
        view.lbl_name.text = mood?.locationsTypeName
        return view
    }
}
