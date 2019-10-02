package emerge.projects.repsolutions.ui.visits.doctorsvisitslist.adaptar

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
import emerge.projects.repsolutions.data.modeldata.ProductList
import kotlinx.android.synthetic.main.listview_doctors_visits_products.view.*

class DoctorVisitsProductsAdaptor (val items: ArrayList<ProductList>, val context: Context) :
    RecyclerView.Adapter<DoctorVisitsProductsAdaptor.ViewHolderDoctorsProductsVisits>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDoctorsProductsVisits {
        return ViewHolderDoctorsProductsVisits(LayoutInflater.from(context).inflate(R.layout.listview_doctors_visits_products, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolderDoctorsProductsVisits, position: Int) {
        var itemPostion = items[position]


        holder?.textviewProduct?.text = itemPostion.productName
        holder?.textviewProductCode?.text = itemPostion.productCode


        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

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
            .load(itemPostion.imageUrl)
            .apply(requestOptions)
            .listener(requestListener)
            .into(holder.imageviewProduct)


    }



    inner class ViewHolderDoctorsProductsVisits(view: View) : RecyclerView.ViewHolder(view) {

        val textviewProduct = view.textview_visits_doc_produts
        val textviewProductCode = view.textview_visits_doc_produts_code
        val imageviewProduct = view.imageView_product


    }
}
