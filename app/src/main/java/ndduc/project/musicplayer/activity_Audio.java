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

import org.w3c.dom.Text;

import java.io.IOException;

import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.URL_Handler.URL_Encoder;

public class activity_Audio extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private Button btnStart, btnPrev, btnNex;
    private TextView viewAudio;
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
        sbAudio = findViewById(R.id.sbAudio);
        listView = findViewById(R.id.lst_audio_);
        btnStart.setOnClickListener(this);
        titles = getIntent().getStringArrayExtra("audios");
        Debug.debug("TEST", titles[1]);
        populateList();
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == btnStart.getId()) {

            Debug.debug("TEST", btnStart.getText());
            if(btnStart.getText().equals("Start")) {
                try {
                    String tit = URL_Encoder.get_URL_Encoder(viewAudio.getText().toString()).replace("+", "%20");
                    Debug.debug("TEST", tit);
                    String u = path + tit;
                    Debug.debug("TEST 2", u);
                    mp = new MediaPlayer();
                    mp.setDataSource(u);
                    mp.prepare();
                    //sbAudio.setMax(mp.getDuration());
                    mHandler = new Handler();
                    activity_Audio.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mp != null){
                                int mCurrentPosition = mp.getCurrentPosition() / 1000;
                                sbAudio.setProgress(mCurrentPosition);
                            }
                            mHandler.postDelayed(this, 1000);
                        }
                    });
                    sbAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(mp != null && fromUser){
                                mp.seekTo(progress * 1000);
                            }
                        }
                    });

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

    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Debug.debug("TEST1", parent.getItemAtPosition(position));
            viewAudio.setText(parent.getItemAtPosition(position).toString());

        }
    };
}
