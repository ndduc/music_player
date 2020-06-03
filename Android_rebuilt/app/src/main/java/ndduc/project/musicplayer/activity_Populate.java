package ndduc.project.musicplayer;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ndduc.project.musicplayer.Container.YoutubeData;
import ndduc.project.musicplayer.Json_Handler.Json_Decoder;

public class activity_Populate extends AppCompatActivity {

    TextView txtApi, txtTitle, txtNum;
    Button btnSearch;
    TableLayout layoutTable;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_populate);
        synComponent();
        try {
            populateImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void synComponent() {
        txtApi = findViewById(R.id.txtApi);
        txtTitle = findViewById(R.id.txtTitle);
        txtNum = findViewById(R.id.txtResult);

        btnSearch = findViewById(R.id.btnSearch);

        layoutTable = findViewById(R.id.layoutTable);
    }

    private void populateImage() throws Exception {

        List<YoutubeData> lst = testYoutubePopulate();

        for (int r = 0; r < lst.size(); r++){
          //  URL img = (URL) lst.get(r).getImage();
            TableRow tr = new TableRow(this);
            for (int c= 0 ; c < 1; c++){
                ImageView im = new ImageView (this);
                Picasso.get().load(lst.get(r).getImage().toString()).into(im);
                im.setPadding(20, 0, 10, 0); //padding in each image if needed
                //add here on click event etc for each image...
                //...
                tr.addView(im, 300,300);
            }
            layoutTable.addView(tr);
        }

    }

    private List<YoutubeData>  testYoutubePopulate () {
        try {
            // JSONObject json = URL_Decoder.readYoutube("AIzaSyDE2igQOTyQ6XgJM03wLazUBF_zmWYWx4Q", "unravel", "1", "DETAIL");
            // Debug.debug("RESULT", json);
            List<YoutubeData> lst = Json_Decoder.getJsonContent("AIzaSyDE2igQOTyQ6XgJM03wLazUBF_zmWYWx4Q", "unravel", "5", "DETAIL");
            return lst;
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
