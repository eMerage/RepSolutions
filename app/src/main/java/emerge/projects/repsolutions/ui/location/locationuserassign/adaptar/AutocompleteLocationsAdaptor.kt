package emerge.projects.repsolutions.ui.location.locationuserassign.adaptar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import emerge.projects.repsolutions.data.modeldata.DistrictList
import emerge.projects.repsolutions.data.modeldata.LocationsList
import emerge.projects.repsolutions.data.modeldata.SpecializationList
import kotlinx.android.synthetic.main.textview_autocomplete.view.*

class AutocompleteLocationsAdaptor(context: Context, @LayoutRes private val layoutResource: Int,
                                   private val list: ArrayList<LocationsList>
) :
    ArrayAdapter<LocationsList>(context, layoutResource, list),
    Filterable {
    private var mPois: ArrayList<LocationsList> = list

    override fun getCount(): Int {
        return mPois.size
    }

    override fun getItem(p0: Int): LocationsList? {
        return mPois[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(context).inflate(layoutResource, parent, false)
        view.lbl_name.text = "${mPois[position].locationsName}"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(context).inflate(layoutResource, parent, false)

        view.lbl_name.text = "${mPois[position].locationsName}"

        return super.getDropDownView(position, view, parent)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val results = FilterResults()
                val queryString = constraint?.toString()

                val query = if (queryString == null || queryString.isEmpty())
                    list
                else
                    list.filter { it.locationsName.toLowerCase().contains(queryString.toLowerCase()) }
                results.values = query
                results.count = query.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
                mPois = results.values as ArrayList<LocationsList>
                if (results.count > 0) notifyDataSetChanged()
                else notifyDataSetInvalidated()
            }

            override fun convertResultToString(result: Any) = (result as LocationsList).locationsName

        }
    }
}
