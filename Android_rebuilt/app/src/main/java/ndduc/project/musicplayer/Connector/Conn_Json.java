package ndduc.project.musicplayer.Connector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import ndduc.project.musicplayer.Helper.Debug;

public class Conn_Json {
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        System.out.println("TEST: " + url);
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd).replace("<pre>","").replace("</pre>","");;
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static JSONObject readJsonFromUrl_2(String url) throws IOException, JSONException {
        System.out.println("TEST: " + url);
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd).replace("<pre>","").replace("</pre>","");
            Debug.debug("TEST", jsonText);
            JSONObject json = new JSONObject(jsonText);
            return null;
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
