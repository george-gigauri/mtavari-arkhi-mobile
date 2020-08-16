package tv.mtavari.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class BootCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            context.startService(Intent(context, BackgroundService::class.java).putExtra("icon", R.mipmap.download).putExtra("placeholder", R.mipmap.ex))
        }
    }
}