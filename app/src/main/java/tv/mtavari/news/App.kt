package tv.mtavari.news

import android.app.Application
import android.os.StrictMode

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}