package ndduc.project.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import ndduc.project.musicplayer.Connector.Conn_Json;
import ndduc.project.musicplayer.Container.YoutubeData;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.Json_Handler.Json_Decoder;
import ndduc.project.musicplayer.Container.Titles;
import ndduc.project.musicplayer.URL_Handler.URL_Decoder;

public class activity_Main extends AppCompatActivity
implements View.OnClickListener{
    //https://code.tutsplus.com/tutorials/create-a-music-player-on-android-project-setup--mobile-22764
    //http://www.java2s.com/Code/Android/Media/PlayMp3filefromaUrl.htm
    private EditText txtTest;
    private Button btnPlay, btnSearch;
    private List<Titles> titleList;
    private String titles[];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.layout_main);
        synComponent();
        addListenner();
        populateTitles();
    }

    private void synComponent() {
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnSearch = findViewById(R.id.btnSearch_main);
    }

    private void addListenner() {
        btnPlay.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == btnPlay.getId()) {
            Debug.debug("TEST", "Test Button");
            populateTitles();
            Intent intent = new Intent(this, activity_Audio.class);
            intent.putExtra("audios", titles);
            startActivity(intent);
        }  else if (v.getId() == btnSearch.getId()) {
            Intent in = new Intent(this, activity_Populate.class);
            startActivity(in);
        }
    }


    /**
     * Populate Titles to Array
     * Note Titles are retreieved from php link
     * This Array will be passed to Player Layout
     * */
    public void populateTitles() {
        try {

            JSONObject json = Conn_Json.readJsonFromUrl("http://192.168.1.243/leeleelookupphp/phpfunction/read_directory/read_dir.php?action=adv_read");
            Json_Decoder jd = new Json_Decoder(json);
            titleList = jd.populateTiles();
            titles = new String[titleList.size()] ;
            for(int i = 0; i < titleList.size(); i++) {
                //System.out.println("MAIN TEST\t" + titleList.get(i).getTitle());
                titles[i] = titleList.get(i).getTitle().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
