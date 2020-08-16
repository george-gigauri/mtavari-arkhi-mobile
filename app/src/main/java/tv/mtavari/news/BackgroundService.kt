package tv.mtavari.news

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random


@Suppress("DEPRECATION")
open class BackgroundService : Service() {
    private lateinit var prefs: SharedPreferences
    private lateinit var prefedit: SharedPreferences.Editor
    private val BASE_URL: String = "https://mtavari.tv/news/archive?page="
    private var PAGE_ID: Int = 1

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread(Runnable {
            while (true) {

                val task = AsyncClass()
                task.execute(BASE_URL, PAGE_ID.toString())

                prefs = getSharedPreferences("LATEST_POST", Context.MODE_PRIVATE)
                val latestLink = prefs.getString("URL", "N")

                if(task.get().size != 1)
                {
                    if (latestLink != task.get()[0]) {
                        pushNotification(task.get()[1], getImage(task.get()[2]!!), task)

                        prefedit = getSharedPreferences("LATEST_POST", Context.MODE_PRIVATE).edit()
                        prefedit.putString("URL", task.get()[0])
                        prefedit.apply()
                    }
                }
                Thread.sleep(1000)
            }
        }).start()
        return START_STICKY
    }

    override fun onDestroy() {
        Log.i("ServiceInfo", "DESTROY")
        startService(Intent(applicationContext, BackgroundService::class.java))
    }


    private fun pushNotification(title: String, img : Bitmap, task : AsyncClass) {
        val r = Random.nextInt(0, 100) + 1

        val intent = Intent(this, ActivityPostView::class.java)
        intent.putExtra("url", task.get()[0])
        intent.putExtra("title", task.get()[1])
        intent.putExtra("img", task.get()[2])
        intent.putExtra("notification_id", r)

        val pIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val b = NotificationCompat.Builder(applicationContext)

        b.setSmallIcon(prefs.getInt("icon", 0))
            .setLargeIcon(BitmapFactory.decodeResource(resources, prefs.getInt("icon", 0)))
            .setPriority(Notification.PRIORITY_HIGH)
            .setContentTitle(title)
            .setSubText(title)
            .setContentIntent(pIntent)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(img))
            .setLights(Color.YELLOW, 1500, 2000)


        val m = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        m.notify(r, b.build())
    }

    private fun getImage(url : String) : Bitmap
    {
        return try{
            val link = URL(url)
            val httpUrlConnection = link.openConnection() as HttpURLConnection
            httpUrlConnection.doInput = true
            httpUrlConnection.connect()
            val inputStream : InputStream = link.openStream()

            BitmapFactory.decodeStream(inputStream)
        } catch (e : Exception) {

        } as Bitmap
    }
}