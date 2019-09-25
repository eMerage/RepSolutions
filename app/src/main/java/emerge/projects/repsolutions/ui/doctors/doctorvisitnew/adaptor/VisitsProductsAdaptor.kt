package emerge.projects.repsolutions.ui.doctors.doctorvisitnew.adaptor

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
import kotlinx.android.synthetic.main.listview_porducts.view.*

class VisitsProductsAdaptor (val items: ArrayList<ProductList>, val context: Context) :
    RecyclerView.Adapter<VisitsProductsAdaptor.ViewHolderDoctorsVisits>() {

    lateinit var mClickListener: ClickListener

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDoctorsVisits {
        return ViewHolderDoctorsVisits(LayoutInflater.from(context).inflate(R.layout.listview_porducts, parent, false))

    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(product: ProductList, aView: View)
    }

    override fun onBindViewHolder(holder: ViewHolderDoctorsVisits, position: Int) {
        var itemPostion = items[position]

        holder.textviewName?.text = itemPostion.productName


        if (itemPostion.isSelect) {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.colorPrimary))
        } else {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.babypowder))
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
            .load(itemPostion.imageUrl)
            .apply(requestOptions)
            .listener(requestListener)
            .into(holder.imageViewProducts)






    }



    inner class ViewHolderDoctorsVisits(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener  {
        val textviewName = view.textview_product_name
        val imageViewProducts = view.imageview_products
        val cardView = view.card_view_docs_products

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            mClickListener.onClick( items[adapterPosition], p0!!)
            items[adapterPosition].isSelect = true
            notifyDataSetChanged()

        }

    }
}
