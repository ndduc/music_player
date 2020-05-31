package ndduc.project.musicplayer.Connector;

import org.json.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.Json_Handler.Json_Decoder;

public class Conn_Json {
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        System.out.println("TEST: " + url);
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
