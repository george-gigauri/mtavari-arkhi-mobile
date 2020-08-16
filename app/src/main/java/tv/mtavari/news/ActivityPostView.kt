package tv.mtavari.news

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_post_view.*
import org.jsoup.Jsoup


class ActivityPostView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_view)

        App().onCreate()

        val notificationId = if (intent.hasExtra("notification_id"))
        {
            intent.getIntExtra("notification_id", 0)
        } else {
            0
        }

        if(notificationId != 0) {
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
        }

        val title = if (intent.hasExtra("title")){
            intent.extras!!.getString("title")
        } else {
            "N/A"
        }

        val category = if (intent.hasExtra("category")){
            intent.extras!!.getString("category")
        } else {
            "N/A"
        }

        val url = if (intent.hasExtra("url")){
            intent.extras!!.getString("url")
        } else {
            "NULL"
        }

        val photo_url = if (intent.hasExtra("img")){
            intent.extras!!.getString("img")
        } else {
            "NULL"
        }

        post_title.text = title
        post_category.text = category

        loadData(url!!, photo_url!!)

        swipe_refresh_1.setOnRefreshListener {
            loadData(url, img = photo_url)
            swipe_refresh_1.isRefreshing = false;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadData(url : String, img : String)
    {
        val content = Jsoup.connect(url).get()
        val published = content.getElementsByClass("id__Published-bhuaj0-13")[0].text()
        val postDescription = content.getElementsByClass("EditorContent__EditorContentWrapper-ygblm0-0")[0]

        Glide.with(this).asBitmap().placeholder(R.mipmap.placeholder).error(getDrawable(R.mipmap.placeholder)).load(img).into(webView)
        post_published.text =  published

        post_text.settings.javaScriptEnabled = true
        post_text.loadDataWithBaseURL("", postDescription.toString(), "text/html", "UTF-8", "");
    }
}
