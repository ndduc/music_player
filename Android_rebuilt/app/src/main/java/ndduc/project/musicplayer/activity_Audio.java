package ndduc.project.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ndduc.project.musicplayer.Converter.cov_Time;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.URL_Handler.URL_Encoder;

public class activity_Audio extends AppCompatActivity {
    private ListView listView;
    private Button btnStart, btnPrev, btnNex, btnAll, btnShuf, btnRe;
    private Button btnHome, btnSearch, btnMusic;
    private TextView viewAudio, viewTime;
    private SeekBar sbAudio;
    private MediaPlayer mp;
    private String titles[];
    private List<String> curTitles;
    private String path; //"http://192.168.1.243/leeleelookupphp/node/audio/";
    private Handler mHandler;
    private Integer current_position;
    private Intent playBack;
    protected void onCreate(Bundle savedInstanceState) {
        path = getResources().getString(R.string.ngrok) + getResources().getString(R.string.server);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player);
        setupComponent();
        setListener();
        titles = getIntent().getStringArrayExtra("audios");
        populateList(listView, titles, listClick, "ORIGINAL");
    }

    protected void setupComponent() {
        btnStart = findViewById(R.id.btnPlay);
        btnNex = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
      //  btnAll = findViewById(R.id.btnAll);
        btnShuf = findViewById(R.id.btnShuf);
        btnRe = findViewById(R.id.btnRe);
        viewAudio = findViewById(R.id.viewSelectedSong);
        viewTime = findViewById(R.id.viewTime);
        sbAudio = findViewById(R.id.sbAudio);
        listView = findViewById(R.id.lst_audio_);
        btnHome = findViewById(R.id.btnTab_Home);


        btnService = findViewById(R.id.btnServiceDebug);
    }

    private void setListener() {
        btnStart.setOnClickListener(eventClick);
        btnNex.setOnClickListener(eventClick);
        btnPrev.setOnClickListener(eventClick);
        btnShuf.setOnClickListener(eventClick);
        btnRe.setOnClickListener(eventClick);
        btnHome.setOnClickListener(eventClick);

        btnService.setOnClickListener(eventClick);
    }

    /**
     * play audio helper, reduce code complexity
     * allow recur auto play
     * option ask for REPEAT or ELSE
     * */
    private void setMediaPlay(final String title, final Integer position, final String option) throws Exception {
        if(titles[position] == null)
            return;
        initPlayer(title, position);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(option.equals("REPEAT")) {
                    try {
                        setMediaPlay(title, position, "REPEAT");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    keepPlaying(position);
                }

            }
        });
    }

    private void initPlayer(String title, Integer position) throws Exception{
        viewAudio.setText(title);
        current_position = position;
        btnStart.setText("Stop");
        mp = new MediaPlayer();
        mp.setDataSource(getURL(title));
        mp.prepare();
        sbAudio.setMax(mp.getDuration() / 1000);
        setSeekBarEvent();
        mp.start();
    }

    /**
     * Algo play on click
     * The moment audio start playing, this algo activate itself
     * Desc: the algo allow program to start next song on the list when the previous song is completed
     * option New indicate - the first time user click on the list
     * option Else indicate - that the list have been previous clicked
     * */
    private void keepPlaying(Integer position) {
        if(titles[position] == null)        //If list is over then deactivate
            return;
        Integer next = 0;
        for(int i = 0; i < titles.length; i++) {
            if(i > position) {
                next = i;
                break;
            }
        }
        final Integer finalNext = next;
        viewAudio.setText(titles[finalNext]);
        try {
            setMediaPlay(titles[finalNext], finalNext, "ELSE");
        } catch  (Exception e ) {
            e.printStackTrace();
        }

    }

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


    /**
     *  List view listener
     *  set selected audion from list to textview onclick
     *  Desc
     *      once clicked, if mediaplayer is playing then stop
     *      else, create new mediaplayer
     *
     * */
    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            curTitles =  populateCurrentTitles(position, titles, curTitles);
            if(mp != null) {
                mp.stop();
                sbAudio.setProgress(0);
            }
            try {
                setMediaPlay(titles[position], position, "ELSE");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                if(btnStart.getText().equals("Start")) {
                } else if (btnStart.getText().equals("Stop")) {
                    mp.stop();
                    btnStart.setText("Start");

                }
            } else if (v.getId() == btnNex.getId()) {
                try {
                    if(mp != null)
                        mp.stop();
                    setMediaPlay(titles[current_position+1], current_position+1, "ELSE");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (v.getId() == btnPrev.getId()) {
                try {
                    if(mp != null)
                        mp.stop();
                    setMediaPlay(titles[current_position-1], current_position-1, "ELSE");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (v.getId() == btnShuf.getId()) {
                if(mp != null)
                    mp.stop();
                populateList(listView, titles, listClick, "RANDOM");
            } else if (v.getId() == btnRe.getId()) {
                if(btnRe.getText().equals("Repeat")) {
                    try {
                        setMediaPlay(titles[current_position], current_position, "REPEAT");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    btnRe.setText("Stop Repeat");
                } else if (btnRe.getText().equals("Stop Repeat")) {
                    try {
                        setMediaPlay(titles[current_position], current_position, "ELSE");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    btnRe.setText("Repeat");
                }

            } else if (v.getId() == btnHome.getId()) {
                Debug.debug("CLICKED", "HOME");
            } else if (v.getId() == btnService.getId()) {
                if(btnService.getText().equals("Service")) {
                    btnService.setText("Postpone");
                    initService();
                    startService();
                } else if (btnService.getText().equals("Postpone")) {
                    btnService.setText("Service");
                    stopService();
                }
            }
        }
    };





    /**
     *Play audio helper, this return url
     * */
    private String getURL(String title) throws Exception{
        String tit = URL_Encoder.get_URL_Encoder(title).replace("+", "%20");
        String u = path + tit;
        return u;
    }


    private String[] randomizeTitles(String[] titles) {
        List<String> strList = Arrays.asList(titles);
        Collections.shuffle(strList);
        titles = strList.toArray(new String[strList.size()]);
        this.titles = titles;       
        return  titles;
    }

    /**
     * Option for
     *  RANDOM and ORIGINAL
     * */
    public ListView populateList(ListView listView, String [] titles, AdapterView.OnItemClickListener listClick, String option) {
        if(titles.length <= 0 || titles == null) {
            Debug.debug("Title List", "Empty");
        } else {
            ArrayAdapter adapter = null;
            if(option.equals("RANDOM")) {
                adapter = new ArrayAdapter<String>(this,
                        R.layout.simplerow, randomizeTitles(titles));
            } else{
                adapter = new ArrayAdapter<String>(this,
                        R.layout.simplerow, titles);
            }
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listClick);
        }
        return listView;
    }

    public List<String> populateCurrentTitles(int position, String[] titles, List<String> curTitles) {
        List tmpAr = new ArrayList<String>();
        for(int i = position; i < titles.length; i++) {
            Debug.debug("Add\t" + i, titles[i]);
            tmpAr.add(titles[i]);
        }
        return  tmpAr;
    }


    /**
     * Trigger Service
     * */

    /**
     * test background player subject of change
     * */

    Button btnService;

    private void initService() {

        //titles
        playBack =new Intent(this, activity_background.class);
        playBack.putExtra("titles", titles);

    }

    private void startService() {
        startService(playBack);
    }

    private void stopService() {
        stopService(playBack);
    }

}
