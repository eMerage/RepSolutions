package emerge.projects.repsolutions.ui.doctors.doctorslocationassign.adaptar

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import emerge.projects.repsolutions.ui.doctors.doctorsnew.adaptar.SpecializationAdaptor
import kotlinx.android.synthetic.main.listview_doctors.view.*
import kotlinx.android.synthetic.main.listview_doctors_list.view.*
import kotlinx.android.synthetic.main.listview_locations.view.*


class LocationApprovedListAdaptor (val items: ArrayList<LocationsList>, val context: Context) :
    RecyclerView.Adapter<LocationApprovedListAdaptor.ViewHolderLocationList>() {

    lateinit var mClickListener: ClickListener

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLocationList {
        return ViewHolderLocationList(LayoutInflater.from(context).inflate(R.layout.listview_locations, parent, false))

    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(loc: LocationsList, aView: View)
    }




    override fun onBindViewHolder(holder: ViewHolderLocationList, position: Int) {
        var itemPostion = items[position]

        holder.textviewLocationsName.text= itemPostion.locationsName



        if (itemPostion.isSelect) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorDarkGreen))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.babypowder))
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



    inner class ViewHolderLocationList(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener  {

        val textviewLocationsName = view.textview_location_name
        val imageviewLocation = view.imageview_location
        val cardView = view.card_view_docs_locations


        init {
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {

            items[adapterPosition].isSelect = !items[adapterPosition].isSelect
            mClickListener.onClick( items[adapterPosition], p0!!)

            notifyDataSetChanged()

        }

    }
}
