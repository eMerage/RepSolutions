package emerge.projects.repsolutions.ui.location.locationlist.adaptar

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
import kotlinx.android.synthetic.main.test.view.*

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


        if(itemPostion.locationsName.isNullOrEmpty()){
            holder?.textviewName?.visibility = View.GONE
            holder?.textviewHintName?.visibility = View.GONE
            holder?.textviewName?.text =""
        }else{
            holder?.textviewName?.visibility = View.VISIBLE
            holder?.textviewHintName?.visibility = View.VISIBLE
            holder?.textviewName?.text =itemPostion.locationsName
        }


        if(itemPostion.locationsAddress.isNullOrEmpty()){
            holder?.textviewAddress?.visibility = View.GONE
            holder?.textviewHintAddress?.visibility = View.GONE
            holder?.textviewAddress?.text =""
        }else{
            holder?.textviewAddress?.visibility = View.VISIBLE
            holder?.textviewHintAddress?.visibility = View.VISIBLE
            holder?.textviewAddress?.text =itemPostion.locationsAddress
        }


        if(itemPostion.locationsArea.isNullOrEmpty()){
            holder?.textviewArea?.visibility = View.GONE
            holder?.textviewHintArea?.visibility = View.GONE
            holder?.textviewArea?.text =""
        }else{
            holder?.textviewArea?.visibility = View.VISIBLE
            holder?.textviewHintArea?.visibility = View.VISIBLE
            holder?.textviewArea?.text =itemPostion.locationsArea
        }



        if(itemPostion.locationsCreatedByName.isNullOrEmpty()){
            holder?.textviewLocationRep?.visibility = View.GONE
            holder?.textviewHintLocationRep?.visibility = View.GONE
            holder?.textviewLocationRep?.text =""
        }else{
            holder?.textviewLocationRep?.visibility = View.VISIBLE
            holder?.textviewHintLocationRep?.visibility = View.VISIBLE
            holder?.textviewLocationRep?.text =itemPostion.locationsCreatedByName
        }


        if(itemPostion.locationsTown.isNullOrEmpty()){
            holder?.textviewTown?.visibility = View.GONE
            holder?.textviewHintTown?.visibility = View.GONE
            holder?.textviewTown?.text =""
        }else{
            holder?.textviewTown?.visibility = View.VISIBLE
            holder?.textviewHintTown?.visibility = View.VISIBLE
            holder?.textviewTown?.text =itemPostion.locationsTown
        }


        if(itemPostion.locationsDistrict.isNullOrEmpty()){
            holder?.textviewDistrict?.visibility = View.GONE
            holder?.textviewHintDistrict?.visibility = View.GONE
            holder?.textviewDistrict?.text =""
        }else{
            holder?.textviewDistrict?.visibility = View.VISIBLE
            holder?.textviewHintDistrict?.visibility = View.VISIBLE
            holder?.textviewDistrict?.text =itemPostion.locationsDistrict
        }


        if(itemPostion.locationsType.isNullOrEmpty()){
            holder?.textviewLocationType?.visibility = View.GONE
            holder?.textviewHintLocationType?.visibility = View.GONE
            holder?.textviewLocationType?.text =""
        }else{
            holder?.textviewLocationType?.visibility = View.VISIBLE
            holder?.textviewHintLocationType?.visibility = View.VISIBLE
            holder?.textviewLocationType?.text =itemPostion.locationsType
        }


        if(itemPostion.locationsApprovalStats.isNullOrEmpty()){
            holder?.textviewStatus?.visibility = View.GONE
            holder?.textviewHintStatus?.visibility = View.GONE
            holder?.textviewStatus?.text =""
        }else{
            holder?.textviewStatus?.visibility = View.VISIBLE
            holder?.textviewHintStatus?.visibility = View.VISIBLE
            holder?.textviewStatus?.text =itemPostion.locationsApprovalStats
        }


        if(itemPostion.locationsLatitude == 0.0){
            holder?.imageviewLocationMap?.visibility = View.GONE
        }else{
            holder?.imageviewLocationMap?.visibility = View.VISIBLE
        }



        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_location_default)
        requestOptions.error(R.drawable.ic_location_default)


        val requestListener = object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Bitmap>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap,
                model: Any,
                target: Target<Bitmap>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }

        Glide.with(context)
            .asBitmap()
            .load(itemPostion.locationsImageUrl)
            .apply(requestOptions)
            .listener(requestListener)
            .into(holder.imageviewLocation)



    }


    inner class ViewHolderLocationList(view: View) : RecyclerView.ViewHolder(view)  {
        val textviewName = view.textview_location_name
        val textviewAddress = view.textview_location_address
        val textviewArea = view.textview_location_area
        val textviewLocationRep = view.textview_location_rep
        val textviewTown = view.textview_location_town
        val textviewDistrict = view.textview_location_district
        val textviewLocationType = view.textview_locationType
        val textviewStatus = view.textview_status


        val textviewHintName = view.textview_hint_location_name
        val textviewHintAddress = view.textview_hint_location_address
        val textviewHintArea = view.textview_hint_location_area
        val textviewHintLocationRep = view.textview_hint_location_rep
        val textviewHintTown = view.textview_hint_location_town
        val textviewHintDistrict = view.textview_hint_location_district
        val textviewHintLocationType = view.textview_hint_location_locationType
        val textviewHintStatus = view.textview_hint_location_status

        val imageviewLocation = view.imageview_locationlist_location
        val imageviewLocationMap = view.image_view_location_map



    }
}
