package emerge.projects.repsolutions.ui.visits.doctorsvisitslist.adaptar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import kotlinx.android.synthetic.main.listview_doctors_visits.view.*

class DoctorVisitsAdaptor (val items: ArrayList<VisitsDoctorsList>, val context: Context) :
    RecyclerView.Adapter<DoctorVisitsAdaptor.ViewHolderDoctorsVisits>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDoctorsVisits {
        return ViewHolderDoctorsVisits(LayoutInflater.from(context).inflate(R.layout.listview_doctors_visits, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolderDoctorsVisits, position: Int) {
        var itemPostion = items[position]

        holder?.textviewVisitno?.text = itemPostion.visitNumber
        holder?.textviewDate?.text = itemPostion.visitDate?.substring(0,10)
        holder?.textviewDoctor?.text = itemPostion.doctorName
        holder?.textviewRep?.text = itemPostion.repName
        holder?.textviewLocation?.text = itemPostion.location

        setVisitsProducts(context,holder,itemPostion.visitsProduct)

    }

    fun setVisitsProducts(context: Context, holder: ViewHolderDoctorsVisits, itemsPro: ArrayList<ProductList>) {
        holder?.recyclerViewVisitsproducts?.adapter = DoctorVisitsProductsAdaptor(itemsPro, context)
    }

    inner class ViewHolderDoctorsVisits(view: View) : RecyclerView.ViewHolder(view) {
        val textviewVisitno = view.textview_visitno
        val textviewDoctor = view.textview_doctor
        val textviewRep = view.textview_rep
        val textviewLocation = view.textview_location
        val textviewDate = view.textview_date
        val recyclerViewVisitsproducts = view.recyclerView_visitsproducts

    }
}
