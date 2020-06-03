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
    private Button btnTest, btnWeb, btnSearch;
    private ListView listView;
    private List<Titles> titleList;
    private String titles[];
    private MediaPlayer mp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp = new MediaPlayer();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.layout_main);
        synComponent();
        addListenner();
        populateTitles();
        populateList();

        try {
           // JSONObject json = URL_Decoder.readYoutube("AIzaSyDE2igQOTyQ6XgJM03wLazUBF_zmWYWx4Q", "unravel", "1", "DETAIL");
           // Debug.debug("RESULT", json);
            List<YoutubeData> lst = Json_Decoder.getJsonContent("AIzaSyDE2igQOTyQ6XgJM03wLazUBF_zmWYWx4Q", "unravel", "5", "DETAIL");
            for(int i = 0; i < lst.size(); i++) {
                lst.get(i).print();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void synComponent() {
        txtTest = (EditText) findViewById(R.id.txtTest);
        btnTest = (Button) findViewById(R.id.btnTest);
        btnSearch = findViewById(R.id.btnSearch_main);
        btnWeb = findViewById(R.id.btnWeb);
        listView = (ListView) findViewById(R.id.lst_audio);
    }

    private void addListenner() {
        btnTest.setOnClickListener(this);
        btnWeb.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == btnTest.getId()) {
            Debug.debug("TEST", "Test Button");

            populateTitles();
            populateList();

            Intent intent = new Intent(this, activity_Audio.class);
            intent.putExtra("audios", titles);
            startActivity(intent);
        } else if (v.getId() == btnWeb.getId()) {
            Intent in = new Intent(this, activity_Convert.class);
            startActivity(in);
        } else if (v.getId() == btnSearch.getId()) {
            Intent in = new Intent(this, activity_Populate.class);
            startActivity(in);
        }
    }

    public void populateTitles() {
        try {

            JSONObject json = Conn_Json.readJsonFromUrl("http://192.168.1.243/leeleelookupphp/php_jsonrw/title_list.json");
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
    public void populateList() {

        if(titles.length <= 0 || titles == null) {
            Debug.debug("Title List", "Empty");
        } else {
            listView = (ListView) findViewById(R.id.lst_audio);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.simplerow, titles);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listClick);

        }

    }

    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Debug.debug("TEST1", parent.getItemAtPosition(position));
/*
            try {
                mp.setDataSource("http://192.168.1.243/leeleelookupphp/youtubedownloader/audio/TsuruNoShikaeshi.mp3");
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    };
}
