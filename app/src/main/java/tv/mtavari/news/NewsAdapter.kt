package tv.mtavari.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView

class NewsAdapter(private val ctx: Context, private val arr: ArrayList<NewsModel>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<RoundedImageView>(R.id.mtavari_img)
        val title = itemView.findViewById<TextView>(R.id.mtavari_title)
        val cat = itemView.findViewById<TextView>(R.id.mtavari_category)
        val tm = itemView.findViewById<TextView>(R.id.mtavari_time)
        val post = itemView.findViewById<ConstraintLayout>(R.id.post_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.news_model, parent, false))
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = arr[position]

        val img = i.thumburl
        val url = i.url
        val title = i.title
        val category = i.category
        val time = i.time

        holder.title.isSelected = true
        holder.title.text = title
        holder.cat.text = category
        holder.tm.text = time

        Glide.with(ctx).asBitmap().placeholder(R.mipmap.placeholder).error(ctx.getDrawable(R.mipmap.placeholder)).load(img).into(holder.image)

        holder.post.setOnClickListener {
            val intent = Intent(ctx, ActivityPostView::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            intent.putExtra("category", category)
            intent.putExtra("img", img)
            ctx.startActivity(intent)
        }
    }
}