package emerge.projects.repsolutions.ui.location.locationlist.adaptar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import kotlinx.android.synthetic.main.listview_doctors_visits.view.*
import kotlinx.android.synthetic.main.listview_doctors_visits.view.textview_location
import kotlinx.android.synthetic.main.listview_locations_list.view.*

class LocationListAdaptor (val items: ArrayList<LocationsList>, val context: Context) :
    RecyclerView.Adapter<LocationListAdaptor.ViewHolderLocationList>() {

    internal var count = 0


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLocationList {
        return ViewHolderLocationList(LayoutInflater.from(context).inflate(R.layout.listview_locations_list, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolderLocationList, position: Int) {
        var itemPostion = items[position]

        holder?.textviewLocation?.text = itemPostion.locationsName
        holder?.textviewAddress?.text = itemPostion.locationsAddress
        holder?.textviewArea?.text = itemPostion.locationsArea


    }



    inner class ViewHolderLocationList(view: View) : RecyclerView.ViewHolder(view)  {
        val textviewLocation = view.textview_location
        val textviewAddress = view.textview_address
        val textviewArea = view.textview_area
        val imageviewLocation = view.image_view_location



    }
}
