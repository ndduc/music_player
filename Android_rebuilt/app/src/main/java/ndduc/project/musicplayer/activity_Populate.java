package ndduc.project.musicplayer;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ndduc.project.musicplayer.Container.YoutubeData;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.Json_Handler.Json_Decoder;
import ndduc.project.musicplayer.URL_Handler.URL_Encoder;

public class activity_Populate extends AppCompatActivity {

    TextView txtApi, txtTitle, txtNum;
    Button btnSearch;
    TableLayout layoutTable;


    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_populate);
        synComponent();
        addListener();

    }

    private void synComponent() {
        txtApi = findViewById(R.id.txtApi);
        txtTitle = findViewById(R.id.txtTitle);
        txtNum = findViewById(R.id.txtResult);

        btnSearch = findViewById(R.id.btnSearch);

        layoutTable = findViewById(R.id.layoutTable);
    }

    private void addListener() {
        btnSearch.setOnClickListener(action);
    }

    View.OnClickListener action = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(v.getId() == btnSearch.getId()) {
                try {
                    populateImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void populateImage() throws Exception {
        layoutTable.removeAllViews();
        final List<YoutubeData> lst = getYoutubeData(txtApi.getText().toString(), URL_Encoder.get_URL_Encoder_Mod_1(txtTitle.getText().toString()), txtNum.getText().toString());

        for (int r = 0; r < lst.size(); r++){
            TableRow tr = new TableRow(this);
            for (int c= 0 ; c < 1; c++){
                final LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout layout_sub = new LinearLayout(this);
                layout_sub.setOrientation(LinearLayout.VERTICAL);
                HorizontalScrollView layout_scroll_Ho = new HorizontalScrollView(this);

                final TextView tit = new TextView(this);
                TextView pub = new TextView(this);
                TextView cha = new TextView(this);
                ImageView im = new ImageView (this);
                Picasso.get().load(lst.get(r).getImage().toString()).into(im);
                im.setPadding(0, 0, 0, 0); //padding in each image if needed

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Debug.debug("CLICK", tit.getText());
                    }
                });

                tit.setText(lst.get(r).getTitles().toString());
                pub.setText(lst.get(r).getPublish().toString());
                cha.setText(lst.get(r).getChannel().toString());
                layout_sub.addView(tit);
                layout_sub.addView(pub);
                layout_sub.addView(cha);
                layout_scroll_Ho.addView(layout_sub);

                layout.addView(im);
                layout.addView(layout_scroll_Ho);
             //   tr.addView(im, 300,300);
                tr.addView(layout);
            }
            layoutTable.addView(tr);
        }

    }

    private List<YoutubeData> getYoutubeData (String api, String title, String num) {
        try {
            // JSONObject json = URL_Decoder.readYoutube("AIzaSyDE2igQOTyQ6XgJM03wLazUBF_zmWYWx4Q", "unravel", "1", "DETAIL");
            // Debug.debug("RESULT", json);

            Json_Decoder jd = new Json_Decoder();
            List<YoutubeData> lst = jd.getJsonContent(api, title, num, "DETAIL");
            return lst;
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
