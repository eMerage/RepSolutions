package emerge.projects.repsolutions.ui.doctors.doctorsnew.adaptar

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
import kotlinx.android.synthetic.main.listview_doctor_duplicate.view.*
import kotlinx.android.synthetic.main.listview_doctors.view.*
import kotlinx.android.synthetic.main.listview_location_duplicate.view.*

class DuplicateDoctorsAdaptor (val items: ArrayList<DoctorList>, val context: Context) :
    RecyclerView.Adapter<DuplicateDoctorsAdaptor.ViewHolderDoctorsVisits>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDoctorsVisits {
        return ViewHolderDoctorsVisits(LayoutInflater.from(context).inflate(R.layout.listview_doctor_duplicate, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolderDoctorsVisits, position: Int) {
        var itemPostion = items[position]

        holder?.textviewName?.text =itemPostion.doctorName
        holder?.textviewContactNumber?.text =itemPostion.doctorContactNo
        holder?.textviewRegNumber?.text =itemPostion.doctorRegistrationNo




        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_doctors_default)
        requestOptions.error(R.drawable.ic_doctors_default)


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
            .load(itemPostion.doctorImageUrl)
            .apply(requestOptions)
            .listener(requestListener)
            .into(holder.imageviewDoc)


    }

    inner class ViewHolderDoctorsVisits(view: View) : RecyclerView.ViewHolder(view) {
        val textviewName = view.textview_duplicate_doctor_name
        val textviewContactNumber = view.textview_duplicate_doctor_contactnumber
        val textviewRegNumber = view.textview_duplicate_doctor_regnumber

        val imageviewDoc = view.imageview_duplicate_doctor


    }
}
