package ndduc.project.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_Convert extends AppCompatActivity implements View.OnClickListener  {

    private Button btnCheck;

    private String url = "http://192.168.1.243/leeleelookupphp/youtubedownloader/";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_convert);

        WebView wv_1 = findViewById(R.id.wvBrowser);
        btnCheck = findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(this);
        wv_1.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnCheck.getId()) {
            Intent in = new Intent(this, activity_Check.class);
            startActivity(in);
        }
    }

}
