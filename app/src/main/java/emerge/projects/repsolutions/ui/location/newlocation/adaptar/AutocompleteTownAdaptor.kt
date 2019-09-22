package emerge.projects.repsolutions.ui.location.newlocation.adaptar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import emerge.projects.repsolutions.data.modeldata.DistrictList
import emerge.projects.repsolutions.data.modeldata.TownList
import kotlinx.android.synthetic.main.textview_autocomplete.view.*

class AutocompleteTownAdaptor(context: Context, @LayoutRes private val layoutResource: Int,
                              private val list: ArrayList<TownList>
) :
    ArrayAdapter<TownList>(context, layoutResource, list),
    Filterable {
    private var mTownList: ArrayList<TownList> = list

    override fun getCount(): Int {
        return mTownList.size
    }

    override fun getItem(p0: Int): TownList? {
        return mTownList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(context).inflate(layoutResource, parent, false)
        view.lbl_name.text = "${mTownList[position].townName}"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(context).inflate(layoutResource, parent, false)

        view.lbl_name.text = "${mTownList[position].townName}"

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
                    list.filter { it.townName.contains(queryString) }
                results.values = query
                results.count = query.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
                mTownList = results.values as ArrayList<TownList>
                if (results.count > 0) notifyDataSetChanged()
                else notifyDataSetInvalidated()
            }

            override fun convertResultToString(result: Any) = (result as TownList).townName

        }
    }
}
