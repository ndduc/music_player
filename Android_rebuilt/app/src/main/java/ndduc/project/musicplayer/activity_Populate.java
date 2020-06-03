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

public class activity_Populate extends AppCompatActivity {

    TextView txtApi, txtTitle, txtNum;
    Button btnSearch;
    TableLayout layoutTable;
    ImageView ivImage_1;

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

        ivImage_1 = findViewById(R.id.imgView_1);
    }

    private void populateImage() throws Exception {

        Picasso.get().load("https://i.ytimg.com/vi/rj7xMBxd5iY/default.jpg").into(ivImage_1);



    }
}
