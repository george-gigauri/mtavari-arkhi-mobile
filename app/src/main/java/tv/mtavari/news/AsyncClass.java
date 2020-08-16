package tv.mtavari.news;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class AsyncClass extends AsyncTask<String, Void, ArrayList<String>> {

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> arr = new ArrayList<>();

        try {
            Document content = Jsoup.connect(strings[0] + strings[1]).get();
            Elements archive = content.getElementsByClass("archive__Content-fe26lv-5 cjXGyg");
            String url = archive.get(0).getElementsByTag("a").get(0).absUrl("href");
            String title = archive.get(0).getElementsByClass("NewsItem__Text-sc-4tbadf-5").first().text();
            String img = archive.get(0).select(".NewsItem__Thumbnail-sc-4tbadf-4 img").attr("src");

            arr.add(url);
            arr.add(title);
            arr.add(img);
        } catch (IOException e) {
            ArrayList<String> err = new ArrayList<>();
            err.add(e.getMessage());
            return err;
        }

        return arr;
    }
}
