package ndduc.project.musicplayer.URL_Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class URL_Encoder {


    public static String get_URL_Encoder(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, String.valueOf(StandardCharsets.UTF_8));
    }




}
