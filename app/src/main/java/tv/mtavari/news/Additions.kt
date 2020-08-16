package tv.mtavari.news

import android.content.Context
import android.net.ConnectivityManager
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Additions(private val context: Context, private val document: Document) {
    private val prefsEditor = context.getSharedPreferences("LATEST_POST", Context.MODE_PRIVATE).edit()

    companion object {
        fun isNetworkConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo
                .isConnected
        }
    }

    fun loadData() : ArrayList<NewsModel> {
        val arr = ArrayList<NewsModel>()
        val archive: Elements = document.getElementsByClass("archive__Content-fe26lv-5 cjXGyg")[0]
            .select("div:has(div)")[0].select("div")[0].getElementsByTag("a")

        for (i in 0 until archive.size) {
            val post = archive[i]

            val img = post.select(".NewsItem__Thumbnail-sc-4tbadf-4 img").attr("src")

            val url = post.absUrl("href")
            val title = post.getElementsByClass("NewsItem__Text-sc-4tbadf-5").text()

            val category =
                post.select(".NewsItem__Category-sc-4tbadf-10").text()
            val time =
                post.select(".NewsItem__Time-sc-4tbadf-9").text()

            if(!title.isNullOrEmpty() && !category.isNullOrEmpty() && !time.isNullOrEmpty())
                arr.add(NewsModel(img, url, title, null, category, time))
        }

        arr.distinctBy { it.url }

        prefsEditor.putString("URL", arr[0].url)
        prefsEditor.putInt("icon", R.mipmap.download)
        prefsEditor.commit()
        prefsEditor.apply()

        return arr
    }
}