package ndduc.project.musicplayer.URL_Handler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URL_Encoder {
    public static String get_URL_Encoder(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, String.valueOf(StandardCharsets.UTF_8));
    }
}
