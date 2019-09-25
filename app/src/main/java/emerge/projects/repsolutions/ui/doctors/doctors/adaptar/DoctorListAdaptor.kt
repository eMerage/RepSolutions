package emerge.projects.repsolutions.ui.doctors.doctors.adaptar

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
import kotlinx.android.synthetic.main.listview_doctors_list.view.*


class DoctorListAdaptor (val items: ArrayList<DoctorList>, val context: Context) :
    RecyclerView.Adapter<DoctorListAdaptor.ViewHolderLocationList>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLocationList {
        return ViewHolderLocationList(LayoutInflater.from(context).inflate(R.layout.listview_doctors_list, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolderLocationList, position: Int) {
        var itemPostion = items[position]

        if(itemPostion.doctorName.isNullOrEmpty()){
            holder?.textviewDoctorsName?.visibility = View.GONE
            holder?.textviewHintDoctorsname?.visibility = View.GONE
            holder?.textviewDoctorsName?.text =""
        }else{
            holder?.textviewDoctorsName?.visibility = View.VISIBLE
            holder?.textviewHintDoctorsname?.visibility = View.VISIBLE
            holder?.textviewDoctorsName?.text =itemPostion.doctorName
        }

        if(itemPostion.allSpecializations.isNullOrEmpty()){
            holder?.textviewDoctorsSpec?.visibility = View.GONE
            holder?.textviewHintDoctorsspec?.visibility = View.GONE
            holder?.textviewDoctorsSpec?.text = ""
        }else{
            holder?.textviewDoctorsSpec?.visibility = View.VISIBLE
            holder?.textviewHintDoctorsspec?.visibility = View.VISIBLE
            holder?.textviewDoctorsSpec?.text = itemPostion.allSpecializations
        }

        if(itemPostion.allLocations.isNullOrEmpty()){
            holder?.textviewDoctorsLocation?.visibility = View.GONE
            holder?.textviewHintDoctorslocation?.visibility = View.GONE
            holder?.textviewDoctorsLocation?.text =""
        }else{
            holder?.textviewDoctorsLocation?.visibility = View.VISIBLE
            holder?.textviewHintDoctorslocation?.visibility = View.VISIBLE
            holder?.textviewDoctorsLocation?.text =itemPostion.allLocations
        }


        if(itemPostion.doctorCreatedByName.isNullOrEmpty()){
            holder?.textviewRep?.visibility = View.GONE
            holder?.textviewHintDoctorsrep?.visibility = View.GONE
            holder?.textviewRep?.text =""
        }else{
            holder?.textviewRep?.visibility = View.VISIBLE
            holder?.textviewHintDoctorsrep?.visibility = View.VISIBLE
            holder?.textviewRep?.text =itemPostion.doctorCreatedByName
        }


        if(itemPostion.doctorContactNo.isNullOrEmpty()){
            holder?.textviewContactNumber?.visibility = View.GONE
            holder?.textviewHintDoctorsnumber?.visibility = View.GONE
            holder?.textviewContactNumber?.text =""
        }else{
            holder?.textviewContactNumber?.visibility = View.VISIBLE
            holder?.textviewHintDoctorsnumber?.visibility = View.VISIBLE
            holder?.textviewContactNumber?.text =itemPostion.doctorContactNo
        }

        if(itemPostion.doctorRegistrationNo.isNullOrEmpty()){
            holder?.textviewRegnumber?.visibility = View.GONE
            holder?.textviewHintDoctorsregnumber?.visibility = View.GONE
            holder?.textviewRegnumber?.text =""
        }else{
            holder?.textviewRegnumber?.visibility = View.VISIBLE
            holder?.textviewHintDoctorsregnumber?.visibility = View.VISIBLE
            holder?.textviewRegnumber?.text =itemPostion.doctorRegistrationNo
        }


        if(itemPostion.doctorQualification.isNullOrEmpty()){
            holder?.textvieQualification?.visibility = View.GONE
            holder?.textviewHintDoctorsqualification?.visibility = View.GONE
            holder?.textvieQualification?.text =""
        }else{
            holder?.textvieQualification?.visibility = View.VISIBLE
            holder?.textviewHintDoctorsqualification?.visibility = View.VISIBLE
            holder?.textvieQualification?.text =itemPostion.doctorQualification
        }


        if(itemPostion.doctorStats.isNullOrEmpty()){
            holder?.textviewStatus?.visibility = View.GONE
            holder?.textviewHintDoctorsstatus?.visibility = View.GONE
            holder?.textviewStatus?.text =""
        }else{
            holder?.textviewStatus?.visibility = View.VISIBLE
            holder?.textviewHintDoctorsstatus?.visibility = View.VISIBLE
            holder?.textviewStatus?.text =itemPostion.doctorStats
        }



        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_doctor)
        requestOptions.error(R.drawable.ic_doctor)


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
            .into(holder.imageviewDoctor)

    }



    inner class ViewHolderLocationList(view: View) : RecyclerView.ViewHolder(view)  {
        val textviewDoctorsName = view.textview_doctors_name
        val textviewDoctorsSpec = view.textview_doctors_spec
        val textviewDoctorsLocation = view.textview_doctors_location
        val textviewRep = view.textview_doc_rep
        val textviewContactNumber = view.textview_contactnumber
        val textviewRegnumber = view.textview_regnumber
        val textvieQualification = view.textview_qualification
        val textviewStatus = view.textview_status


        val textviewHintDoctorsname = view.textview_hint_doctors_name
        val textviewHintDoctorsspec = view.textview_hint_doctors_spec
        val textviewHintDoctorslocation = view.textview_hint_doctors_location
        val textviewHintDoctorsrep = view.textview_hint_doctors_rep
        val textviewHintDoctorsnumber = view.textview_hint_doctors_number
        val textviewHintDoctorsregnumber = view.textview_hint_doctors_regnumber
        val textviewHintDoctorsqualification = view.textview_hint_doctors_qualification
        val textviewHintDoctorsstatus = view.textview_hint_doctors_status



        val imageviewDoctor = view.imageview_doctor



    }
}
