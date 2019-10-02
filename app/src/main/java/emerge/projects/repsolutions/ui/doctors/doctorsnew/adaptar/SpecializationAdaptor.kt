package emerge.projects.repsolutions.ui.doctors.doctorsnew.adaptar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
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
import kotlinx.android.synthetic.main.listview_location_duplicate.view.*
import kotlinx.android.synthetic.main.listview_specialization.view.*

class SpecializationAdaptor (val items: ArrayList<SpecializationList>, val context: Context) :
    RecyclerView.Adapter<SpecializationAdaptor.ViewHolderDoctorsVisits>() {

    lateinit var mClickListener: ClickListener

    init {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDoctorsVisits {
        return ViewHolderDoctorsVisits(LayoutInflater.from(context).inflate(R.layout.listview_specialization, parent, false))

    }
    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(spec: SpecializationList, aView: View)
    }

    override fun onBindViewHolder(holder: ViewHolderDoctorsVisits, position: Int) {
        var itemPostion = items[position]
        holder.textviewSpec.text = itemPostion.specName

        if (itemPostion.isSelectSpec) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.babypowder))
        }

    }

    inner class ViewHolderDoctorsVisits(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val textviewSpec = view.textview_spec
        val cardView = view.card_view_doc_spec

        init {
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            items[adapterPosition].isSelectSpec = !items[adapterPosition].isSelectSpec
            mClickListener.onClick( items[adapterPosition], p0!!)



            notifyDataSetChanged()

        }

    }
}
