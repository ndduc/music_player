package ndduc.project.musicplayer;

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
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.Json_Handler.Json_Decoder;
import ndduc.project.musicplayer.Json_Handler.Titles;

public class activity_Main extends AppCompatActivity
implements View.OnClickListener{
    //https://code.tutsplus.com/tutorials/create-a-music-player-on-android-project-setup--mobile-22764
    //http://www.java2s.com/Code/Android/Media/PlayMp3filefromaUrl.htm
    private EditText txtTest;
    private Button btnTest;
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
        txtTest = (EditText) findViewById(R.id.txtTest);
        btnTest = (Button) findViewById(R.id.btnTest);
        listView = (ListView) findViewById(R.id.lst_audio);
        btnTest.setOnClickListener(this);
        populateTitles();
        populateList();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == btnTest.getId()) {
            Debug.debug("TEST", "Test Button");

            populateTitles();
            populateList();
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

            try {
                mp.setDataSource("http://192.168.1.243/leeleelookupphp/youtubedownloader/audio/TsuruNoShikaeshi.mp3");
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
