package tv.mtavari.news

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.TokenWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class MainActivity : AppCompatActivity() {
    private val BASE_URL: String = "https://mtavari.tv/news/archive?page="
    private var PAGE_ID: Int = 1
    private lateinit var adapter: NewsAdapter
    lateinit var additions: Additions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App().onCreate()

        startService(Intent(this, BackgroundService::class.java))

        if(Additions.isNetworkConnected(this)) {
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()

            val content: Document = Jsoup.connect(BASE_URL + PAGE_ID.toString()).get()
            additions = Additions(this, content)

            adapter = NewsAdapter(this, additions.loadData())

            recyclerview.adapter = adapter

            swipe_refresh.setOnRefreshListener {
                recreate()
            }
        } else {
            Toast.makeText(this, "ინტერნეტ კავშირი არ არის!", Toast.LENGTH_LONG).show()
        }
    }
}
