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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ndduc.project.musicplayer.Converter.cov_Time;
import ndduc.project.musicplayer.Helper.Converter;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.Option.playOption;
import ndduc.project.musicplayer.URL_Handler.URL_Encoder;

public class activity_Audio extends AppCompatActivity {
    private ListView listView;
    private Button btnStart, btnPrev, btnNex, btnAll, btnShuf, btnRe;
    private TextView viewAudio, viewTime;
    private SeekBar sbAudio;
    private MediaPlayer mp;
    private String titles[];
    private List<String> curTitles;
    private String path = "http://192.168.1.243/leeleelookupphp/youtubedownloader/audio/";
    private Handler mHandler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player);
        setupComponent();
        setListener();
        titles = getIntent().getStringArrayExtra("audios");
        Debug.debug("TEST", titles[1]);
        populateList();
    }


    protected void setupComponent() {
        btnStart = findViewById(R.id.btnPlay);
        btnNex = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        btnAll = findViewById(R.id.btnAll);
        btnShuf = findViewById(R.id.btnShuf);
        btnRe = findViewById(R.id.btnRe);

        viewAudio = findViewById(R.id.viewSelectedSong);
        viewTime = findViewById(R.id.viewTime);

        sbAudio = findViewById(R.id.sbAudio);

        listView = findViewById(R.id.lst_audio_);
    }

    private void setListener() {
        btnStart.setOnClickListener(eventClick);
        btnNex.setOnClickListener(eventClick);
        btnPrev.setOnClickListener(eventClick);
        btnAll.setOnClickListener(eventClick);
        btnShuf.setOnClickListener(eventClick);
        btnRe.setOnClickListener(eventClick);
    }




    /**
     * Populate data for listview on launch
     * titles[] is variable from main acitivy
     * */
    private void populateList() {
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
     * Play audio
     * */
    private void playAudion(Integer option) throws Exception{


        if (option == playOption.single) {
            setMediaPlay(viewAudio.getText().toString());
            btnStart.setText("Stop");
        } else if (option == playOption.all) {

        } else if (option == playOption.repeat) {

        } else if (option == playOption.conti) {
            List<String> lst = curTitles;
            setMediaPlay(lst);

        }
    }

    /**
    *Play audio helper, this return url
    * */
    private String getURL(String title) throws Exception{
        String tit = URL_Encoder.get_URL_Encoder(title).replace("+", "%20");
        String u = path + tit;
        return u;
    }

    /**
     * play audio helper, reduce code complexity
     * */
    private void setMediaPlay(String title) throws Exception {
        viewAudio.setText(testList.get(z));
        btnStart.setText("Stop");
        Debug.debug("Playing", testList.get(z));
        mp = new MediaPlayer();
        mp.setDataSource(getURL(title));
        mp.prepare();
        //Debug.debug("DURATION",mp.getDuration());
        sbAudio.setMax(mp.getDuration() / 1000);
        setSeekBarEvent();
        mp.start();
    }

    /**
     * Set audio for auto play
     * */
    int z = 0;
    List<String> testList;
    private void setMediaPlay  (List<String> lst) throws  Exception{
        testList = lst;
        Debug.debug_List("Test", testList);
        Debug.debug("Playing", testList.get(z));
            setMediaPlay(testList.get(z));
            viewAudio.setText(testList.get(z));
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    z++;

                    if(testList.get(z) != null) {
                        try {
                            setMediaPlay(testList.get(z));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });



    }

/**
 *  Algorithm eliminate all element before selected element and keep the remaining
 *  Tend to populate the autoplay
 *  [0 1 2]
 *  selected [1]
 *  algo remove [0]
 *  must remain [1 2]
 *
 *  arr_1 = [0 1 2] where size = 3
 *      select 1 then size = 2; element before 2 must be remove
 *
 * */
    private void populateCurrentTitles(int position) {
        List tmpAr = new ArrayList<String>();
        for(int i = position; i < titles.length; i++) {
            Debug.debug("Add\t" + i, titles[i]);
            tmpAr.add(titles[i]);
        }
        curTitles = tmpAr;
        viewAudio.setText(curTitles.get(curTitles.size()-1));
    }





    /**
     *  List view listener
     *  set selected audion from list to textview onclick
     * */
    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
//[ 0, 1, 2*, 3]        got 2 wnt to get rid of 0 and 1
//[2, 3]
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            populateCurrentTitles(position);
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
                        playAudion(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (btnStart.getText().equals("Stop")) {
                    mp.stop();
                    btnStart.setText("Start");
                }
            } else if (v.getId() == btnNex.getId()) {

            } else if (v.getId() == btnPrev.getId()) {

            } else if (v.getId() == btnAll.getId()) {
                try {
                    playAudion(4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (v.getId() == btnShuf.getId()) {

            } else if (v.getId() == btnRe.getId()) {

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
