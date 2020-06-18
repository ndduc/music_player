package ndduc.project.musicplayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.URL_Handler.URL_Encoder;


public class activity_background extends Service  {
    private static final String TAG = null;
    MediaPlayer player;
    String[] titles;
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
       // titles = getIntent().getStringArrayExtra("audios");



    }

    int position =1 ;
    private void initPlayer(int position) {

        if (titles[position] == null)
            this.position = 0;
        if(player != null)
            player = null;
        player = new MediaPlayer();
        try {
            Debug.debug("TEST TITLE", titles[0] );
            player.setDataSource(getResources().getString(R.string.ngrok) + "/leeleelookupphp/Node/audio/" + getURL(titles[this.position]));
            player.prepare();
            player.start();
            this.position++;
            final int finalPosition = this.position;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    try {
                        initPlayer(finalPosition);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e ) {
            e.printStackTrace();
        }

        player.setLooping(false); // Set looping
        player.setVolume(100,100);
    }

    /**
     *Play audio helper, this return url
     * */
    private String getURL(String title) throws Exception{
        String tit = URL_Encoder.get_URL_Encoder(title).replace("+", "%20");
        return tit;
    }

    public int onStartCommand (Intent intent, int flags, int startId) {
        titles =(String[]) intent.getExtras().get("titles");
        initPlayer(position);
        return 1;
    }


    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }

}