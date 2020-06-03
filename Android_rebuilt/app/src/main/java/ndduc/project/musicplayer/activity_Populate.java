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


    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_populate);
        synComponent();
        try {
            populateTable();
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

    private void populateTable() throws Exception {

        for (int r=1; r<= 1; r++) {
            TableRow tr = new TableRow(this);
            for (int c = 1; c <= 1; c++) {
                ImageView im = new ImageView(this);
                Picasso.get().load("https://i.ytimg.com/vi/rj7xMBxd5iY/default.jpg").into(im);

                tr.addView(im, 100, 100);
            }
            layoutTable.addView(tr);
        }
    }
}
