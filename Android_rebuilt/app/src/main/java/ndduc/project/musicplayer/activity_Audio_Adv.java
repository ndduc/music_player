package ndduc.project.musicplayer;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import ndduc.project.musicplayer.Helper.Debug;

public class activity_Audio_Adv extends AppCompatActivity
     {

    private Button btnPlayList, btnConfig;
    private LayoutInflater mInflater;       //Used to dynamically add view to layout


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.layout_music_man);
        setupComponent();
        setupListener();


        mInflater = LayoutInflater.from(this);

    }

    private void setupComponent() {
        btnPlayList = findViewById(R.id.btnPlayList_ADV);
        btnConfig = findViewById(R.id.btnConfig_ADV);
    }

    private void setupListener() {
        btnPlayList.setOnClickListener(action);
        btnConfig.setOnClickListener(action);
    }


    View.OnClickListener action = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == btnPlayList.getId()) {
                createPopUp_Playlist(v);
            } else if (v.getId() == btnConfig.getId()) {
                createPopUp_Config(v);
            }
        }
    };


    /**Following consist code for popup layout*/
    /**Code for Config Popup
     * Config give user power such as create new dir, remove, copy, move file across the directory*/
    Button btnCreate_Mod, btnMod_Mod, btnReturn_Mod;
    LinearLayout lay_menured;
    PopupWindow popupWindow;
    private void createPopUp_Config(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_pop_menu_red, null);
        popupView.setAnimation(AnimationUtils.loadAnimation(this, R.animator.popupanim));
        setupComponent_Mod(popupView);
        setupListener_Mod();
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height =  LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    private void setupComponent_Mod(View v) {
        btnCreate_Mod = v.findViewById(R.id.btnCreate_Menu_Red);
        btnMod_Mod = v.findViewById(R.id.btnMod_Menu_Red);
        btnReturn_Mod = v.findViewById(R.id.btnReturn_Menu_Red);
        lay_menured = v.findViewById(R.id.lay_menu_red);
    }

    private void setupListener_Mod() {
        btnCreate_Mod.setOnClickListener(mod_action);
        btnMod_Mod.setOnClickListener(mod_action);
        btnReturn_Mod.setOnClickListener(mod_action);
    }

    private Spinner spinAction;
    private View pop_modify;
     View.OnClickListener mod_action = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(v.getId() == btnCreate_Mod.getId()) {
                Debug.debug("TEST", "SUCCESS");
                if(pop_modify != null) {
                    lay_menured.removeView(pop_modify);
                }

                pop_modify = mInflater.inflate(R.layout.layout_pop_create , null, false);
                lay_menured.addView(pop_modify);

            } else if (v.getId() == btnReturn_Mod.getId()) {
                popupWindow.dismiss();
            } else if (v.getId() == btnMod_Mod.getId()) {
                if(pop_modify != null) {
                    lay_menured.removeView(pop_modify);
                }

                pop_modify = mInflater.inflate(R.layout.layout_pop_modify , null, false);
                setActionSpinner();

                lay_menured.addView(pop_modify);
             }
         }
     };

     /**
      * Setup spinner for modify layout
      * Action spinner allow a proper action to popup upon selection
      * This layout come along with a listview - it will be generate upon playlist selection
      * */

     private void setActionSpinner() {
         spinAction = pop_modify.findViewById(R.id.spinPlayList_Option);

         final String[] arrAction = new String[] {
                 "Selection", "Copy", "Move", "Delete"
         };
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(pop_modify.getContext(),
                 android.R.layout.simple_spinner_item, arrAction);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinAction.setAdapter(adapter);

         spinAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 Debug.debug("TEST", arrAction[position]);
                 if(arrAction[position].equals("Copy")) {

                 } else if (arrAction[position].equals("Move")) {

                 } else if (arrAction[position].equals("Delete")) {

                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });
     }



     /**Following code contain code for playlist function
      * brief: playlist function allow user to select playlist as desire
      * */


     private void createPopUp_Playlist(View v) {
         LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
         View popupView = inflater.inflate(R.layout.layout_pop_playlist_selector, null);
         popupView.setAnimation(AnimationUtils.loadAnimation(this, R.animator.popupanim));
         int width = LinearLayout.LayoutParams.MATCH_PARENT;
         int height =  LinearLayout.LayoutParams.WRAP_CONTENT;

         boolean focusable = true; // lets taps outside the popup also dismiss it
         popupWindow = new PopupWindow(popupView, width, height, focusable);
         popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
     }
 }
