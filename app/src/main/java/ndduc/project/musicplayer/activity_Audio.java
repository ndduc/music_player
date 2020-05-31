package ndduc.project.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import ndduc.project.musicplayer.Converter.cov_Time;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.URL_Handler.URL_Encoder;

public class activity_Audio extends AppCompatActivity {
    private ListView listView;
    private Button btnStart, btnPrev, btnNex;
    private TextView viewAudio, viewTime;
    private SeekBar sbAudio;
    private MediaPlayer mp;
    private String titles[];
    private String path = "http://192.168.1.243/leeleelookupphp/youtubedownloader/audio/";
    private Handler mHandler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player);
        btnStart = findViewById(R.id.btnPlay);
        btnNex = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        viewAudio = findViewById(R.id.viewSelectedSong);
        viewTime = findViewById(R.id.viewTime);
        sbAudio = findViewById(R.id.sbAudio);
        listView = findViewById(R.id.lst_audio_);
        btnStart.setOnClickListener(eventClick);
        titles = getIntent().getStringArrayExtra("audios");
        Debug.debug("TEST", titles[1]);
        populateList();
    }



    /**
     * Populate data for listview on launch
     * titles[] is variable from main acitivy
     * */
    public void populateList() {
        if(titles.length <= 0 || titles == null) {
            Debug.debug("Title List", "Empty");
        } else {
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.simplerow, titles);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listClick);
        }
    }

    /**
     *  List view listener
     *  set selected audion from list to textview onclick
     * */
    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Debug.debug("TEST1", parent.getItemAtPosition(position));
            viewAudio.setText(parent.getItemAtPosition(position).toString());

        }
    };


    /**
     * Action for seekbar
     *      music direct to designated segment on click
     * */
    SeekBar.OnSeekBarChangeListener seekListner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(mp != null && fromUser){
                mp.seekTo(progress * 1000);
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };

    View.OnClickListener eventClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == btnStart.getId()) {
                Debug.debug("TEST", btnStart.getText());
                if(btnStart.getText().equals("Start")) {
                    try {
                        String tit = URL_Encoder.get_URL_Encoder(viewAudio.getText().toString()).replace("+", "%20");
                        String u = path + tit;
                        mp = new MediaPlayer();
                        mp.setDataSource(u);
                        mp.prepare();

                       // cov_Time.getTime(mp.getDuration());
                        Debug.debug("DURATION",mp.getDuration());
                        sbAudio.setMax(mp.getDuration() / 1000);
                        setSeekBarEvent();
                        mp.start();

                        btnStart.setText("Stop");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (btnStart.getText().equals("Stop")) {
                    mp.stop();
                    btnStart.setText("Start");
                }
            }
        }
    };


    /**
     * This here handle seekbar duration event*/
    private void setSeekBarEvent() {
        mHandler = new Handler();
        activity_Audio.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mp != null){

                    int mCurrentPosition = mp.getCurrentPosition() / 1000;
                    sbAudio.setProgress(mCurrentPosition);
                    viewTime.setText(cov_Time.getTime(mp.getCurrentPosition()));
                    mHandler.postDelayed(this, 1000);
                }

            }
        });
        sbAudio.setOnSeekBarChangeListener(seekListner);
    }
}
