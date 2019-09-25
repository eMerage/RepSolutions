package emerge.projects.repsolutions.ui.location.locationnew.adaptar

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
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import kotlinx.android.synthetic.main.listview_location_duplicate.view.*

class DuplicateLocationsAdaptor (val items: ArrayList<LocationsList>, val context: Context) :
    RecyclerView.Adapter<DuplicateLocationsAdaptor.ViewHolderDoctorsVisits>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDoctorsVisits {
        return ViewHolderDoctorsVisits(LayoutInflater.from(context).inflate(R.layout.listview_location_duplicate, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolderDoctorsVisits, position: Int) {
        var itemPostion = items[position]

        holder?.textviewRep?.text ="Rep   "+itemPostion.locationsName
        holder?.textviewLocationName?.text ="Name  "+itemPostion.locationsName
        holder?.textviewLocationType?.text ="Type  "+ itemPostion.locationsType



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

    inner class ViewHolderDoctorsVisits(view: View) : RecyclerView.ViewHolder(view) {
        val textviewLocationName = view.textview_duplicate_location_name
        val textviewLocationType = view.textview_duplicate_location_type
        val textviewRep = view.textview_duplicate_location_rep

        val imageviewLocation = view.imageview_duplicate_location


    }
}
