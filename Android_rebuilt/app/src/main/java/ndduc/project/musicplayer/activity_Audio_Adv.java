package ndduc.project.musicplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import ndduc.project.musicplayer.Connector.Conn_Json;
import ndduc.project.musicplayer.Container.Directory;
import ndduc.project.musicplayer.Container.Titles;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.Json_Handler.Json_Decoder;

public class activity_Audio_Adv extends AppCompatActivity
     {

    private Button btnPlayList, btnConfig;
    private LayoutInflater mInflater;       //Used to dynamically add view to layout
    private ListView list_player;
    private ArrayAdapter adapter_player;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.layout_music_man);
        setupComponent();
        setupListener();


        mInflater = LayoutInflater.from(this);

    }

     private void setAdaper(String folder) {
         adapter_player = new ArrayAdapter<String>(this,
                 R.layout.simplerow, populateTitles("adv", folder));
     }
    private void setupComponent() {
        btnPlayList = findViewById(R.id.btnPlayList_ADV);
        btnConfig = findViewById(R.id.btnConfig_ADV);
        list_player = findViewById(R.id.lst_audio_2);
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
    View popupView;
    @SuppressLint("ResourceType")
    private void createPopUp_Config(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.layout_pop_menu_red, null);
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

    private Spinner spinAction, spinMod, spinCreate;
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
                setActionSpinner_Create();
                lay_menured.addView(pop_modify);
            } else if (v.getId() == btnReturn_Mod.getId()) {
                popupWindow.dismiss();
            } else if (v.getId() == btnMod_Mod.getId()) {
                if(pop_modify != null) {
                    lay_menured.removeView(pop_modify);
                }
                pop_modify = mInflater.inflate(R.layout.layout_pop_modify , null, false);
                setActionSpinner_Mod();
                lay_menured.addView(pop_modify);
             }
         }
     };

     /**
      * Setup spinner for modify layout
      * Action spinner allow a proper action to popup upon selection
      * This layout come along with a listview - it will be generate upon playlist selection
      * */

     private void setActionSpinner_Mod() {
         spinAction = pop_modify.findViewById(R.id.spinPlayList_Option);
         spinMod = pop_modify.findViewById(R.id.spinPlayList_Mod);

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


         final String[] arrMod = populateDirectory();
         ArrayAdapter<String> adapter_2 = new ArrayAdapter<String>(pop_modify.getContext(),
                 android.R.layout.simple_spinner_item, arrMod);
         adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinMod.setAdapter(adapter_2);
         spinMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 Debug.debug("TEST", arrMod[position]);
                 playlistContent(pop_modify, "mod", "mod", arrMod[position]);
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });



     }


     /**Spiner for Create Option*/
     private void setActionSpinner_Create() {
         spinCreate = pop_modify.findViewById(R.id.spinDir_create);
         final String[] arrMod = populateDirectory();
         ArrayAdapter<String> adapter_2 = new ArrayAdapter<String>(pop_modify.getContext(),
                 android.R.layout.simple_spinner_item, arrMod);
         adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinCreate.setAdapter(adapter_2);
         spinCreate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 Debug.debug("TEST", arrMod[position]);
                 if(arrMod[position].equals("Copy")) {

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

         Spinner spinPlayList;
         View popupView_Playlist;
         Button btnReturn_PlayList, btnSelect_playList;
     @SuppressLint("ResourceType")
     private void createPopUp_Playlist(View v) {
         LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
         popupView_Playlist = inflater.inflate(R.layout.layout_pop_playlist_selector, null);
         popupView_Playlist.setAnimation(AnimationUtils.loadAnimation(this, R.animator.popupanim));
         int width = LinearLayout.LayoutParams.MATCH_PARENT;
         int height =  LinearLayout.LayoutParams.WRAP_CONTENT;
         setActionSpinner_Playlist();
         btnReturn_PlayList = popupView_Playlist.findViewById(R.id.btnReturn_playlist);
         btnSelect_playList = popupView_Playlist.findViewById(R.id.btnSelect_playlist);
         btnReturn_PlayList.setOnClickListener(playlist_action);
         btnSelect_playList.setOnClickListener(playlist_action);
         boolean focusable = true; // lets taps outside the popup also dismiss it
         popupWindow = new PopupWindow(popupView_Playlist, width, height, focusable);
         popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

     }

         /**Spiner for Create Option*/
         private void setActionSpinner_Playlist() {
             spinPlayList = popupView_Playlist.findViewById(R.id.spinPlayList);
             final String[] arrMod = populateDirectory();
             ArrayAdapter<String> adapter_2 = new ArrayAdapter<String>(popupView_Playlist.getContext(),
                     android.R.layout.simple_spinner_item, arrMod);
             adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             spinPlayList.setAdapter(adapter_2);
             spinPlayList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                     Debug.debug("TEST", arrMod[position]);
                     setAdaper(arrMod[position]);
                    playlistContent(popupView_Playlist, "playlist", "playlist", arrMod[position]);
                     if(arrMod[position].equals("Copy")) {

                     }
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here
                 }

             });
         }

         View.OnClickListener playlist_action = new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if(v.getId() == btnReturn_PlayList.getId()) {
                     popupWindow.dismiss();
                 } else if (v.getId() == btnSelect_playList.getId()) {
                     //playlistContent(popupView_Playlist, "else", "else", "");

                     /*
                     ArrayAdapter adapter = new ArrayAdapter<String>(this.getContext(),
                             R.layout.simplerow, populateTitles("adv", folder));
                     list.setAdapter(adapter);*/
                     list_player.setAdapter(adapter_player);
                     popupWindow.dismiss();

                 }
             }
         };


         /**Following code contain generic code which will be use across the aplication*/
     public String[] populateDirectory() {
         JSONObject json = null;
         try {
             List<Directory> dirList;
             String dir[];

                 json = Conn_Json.readJsonFromUrl("http://192.168.1.243/leeleelookupphp/phpfunction/read_directory/read_dir.php?action=folderadv");

             Json_Decoder jd = new Json_Decoder(json);
             dirList = jd.populateDirectory();
             dir = new String[dirList.size()] ;
             for(int i = 0; i < dirList.size(); i++) {
                 System.out.println("MAIN TEST\t" + dirList.get(i).getDir());
                 dir[i] = dirList.get(i).getDir().toString();
             }
             return dir;
         } catch (IOException e) {
             e.printStackTrace();
             return null;
         } catch (JSONException e) {
             e.printStackTrace();
             return null;
         }
     }


         /**
          * Populate Titles to Array
          * Note Titles are retreieved from php link
          * This Array will be passed to Player Layout
          * http://192.168.1.243/leeleelookupphp/phpfunction/read_directory/read_dir.php?action=advspec&spec=test_list_1
          * */
         public String[] populateTitles(String option, String folder) {
             JSONObject json = null;
             try {
                    if(option.equals("adv")) {
                        json = Conn_Json.readJsonFromUrl(" http://192.168.1.243/leeleelookupphp/phpfunction/read_directory/read_dir.php?action=advspec&spec=" + folder);
                    } else {
                        json = Conn_Json.readJsonFromUrl("http://192.168.1.243/leeleelookupphp/phpfunction/read_directory/read_dir.php?action=adv_read");
                    }
                 Json_Decoder jd = new Json_Decoder(json);
                 List<Titles> titleList = jd.populateTiles();
                 String[] titles = new String[titleList.size()] ;
                 for(int i = 0; i < titleList.size(); i++) {
                     titles[i] = titleList.get(i).getTitle().toString();
                 }
                 return titles;
             } catch (IOException e) {
                 e.printStackTrace();
                 return null;
             } catch (JSONException e) {
                 e.printStackTrace();
                return null;
             }
         }


         /**Generate Playlist contents*/
         public void playlistContent(View view, String option, String opt, String folder) {
             ListView list = null;
             if(option.equals("mod")) {
                 list = view.findViewById(R.id.lst_playlist_mod);
             } else if (option.equals("playlist")) {
                 list = view.findViewById(R.id.lst_playlist);
             }

             if(opt.equals("debug")) {
                 ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(),
                         R.layout.simplerow, populateTitles("else", null));
                 list.setAdapter(adapter);
             } else if (opt.equals("mod")) {
                 ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(),
                 R.layout.simplerow, populateTitles("adv", folder));
                 list.setAdapter(adapter);
             } else if (opt.equals("playlist")) {
                 ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(),
                 R.layout.simplerow, populateTitles("adv", folder));
                 list.setAdapter(adapter);
             }

         }
 }
