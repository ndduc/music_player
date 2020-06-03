package ndduc.project.musicplayer;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_Check extends AppCompatActivity {
    private String url = "http://192.168.1.243/leeleelookupphp/print.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_check);

        WebView wv_1 = findViewById(R.id.wvBrowser);
        wv_1.loadUrl(url);
    }
}
