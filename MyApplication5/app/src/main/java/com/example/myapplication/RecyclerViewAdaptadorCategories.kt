import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Category
import com.example.myapplication.R
import com.example.myapplication.SelectCategory

class RecyclerViewAdaptadorCategories(
    private val contexto: SelectCategory,
    private val lista: ArrayList<Category>,
    private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<RecyclerViewAdaptadorCategories.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageCategoryView: ImageView
        val btnNameCategory: Button

        init {
            imageCategoryView = view.findViewById(R.id.image_category)
            btnNameCategory = view.findViewById(R.id.btn_category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_view_categories, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = lista[position]
        holder.imageCategoryView.setImageResource(getImageResourceId(category.imageId.toString()))
        holder.btnNameCategory.text = category.name

        holder.btnNameCategory.setOnClickListener {
            itemClickListener.onItemClick(category)
        }
    }

    // Add a function to get the image resource ID based on the image name
    private fun getImageResourceId(imageName: String?): Int {
        return contexto.resources.getIdentifier(imageName, "drawable", contexto.packageName)
    }
    fun updateData(newData: ArrayList<Category>) {
        lista.clear()
        lista.addAll(newData)
        notifyDataSetChanged()
    }
}

