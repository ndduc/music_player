package ndduc.project.musicplayer.URL_Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class URL_Decoder {
    /**
     * Read youtube api
     * */

    public static JSONObject readYoutube(String key, String title, String result, String option) throws IOException, JSONException {
        String url = "https://www.googleapis.com/youtube/v3/search?" + "maxResults=" + result + "&q=" + title + "&key=" + key;
        String searchMode = "snippet"; //&part=
        if(option.equals("DETAIL")) {
            return readJsonFromUrl(url + "&part=" + searchMode);
        } else if (option.equals("BASIC")) {
            return readJsonFromUrl(url);
        } else {
            return readJsonFromUrl(url);
        }
    }

    /**
     * 	Method perform read from URL
     * */
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);


            return json;
        } finally {
            is.close();
        }
    }

    /**
     * 	Helper method for readJsonFromUrl
     * */
    private  static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
