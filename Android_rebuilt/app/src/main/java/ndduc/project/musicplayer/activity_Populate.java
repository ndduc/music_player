package ndduc.project.musicplayer;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

import ndduc.project.musicplayer.Container.YoutubeData;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.Json_Handler.Json_Decoder;

import ndduc.project.musicplayer.URL_Handler.URL_Encoder;

public class activity_Populate extends AppCompatActivity {

    TextView txtApi, txtTitle, txtNum;
    Button btnSearch;
    TableLayout layoutTable;


    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_populate);
        synComponent();
        addListener();

    }

    private void synComponent() {
        txtApi = findViewById(R.id.txtApi);
        txtTitle = findViewById(R.id.txtTitle);
        txtNum = findViewById(R.id.txtResult);

        btnSearch = findViewById(R.id.btnSearch);

        layoutTable = findViewById(R.id.layoutTable);
    }

    private void addListener() {
        btnSearch.setOnClickListener(action);
    }

    View.OnClickListener action = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(v.getId() == btnSearch.getId()) {
                try {
                    populateImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void populateImage() throws Exception {
        layoutTable.removeAllViews();
        final List<YoutubeData> lst = getYoutubeData(txtApi.getText().toString(), URL_Encoder.get_URL_Encoder_Mod_1(txtTitle.getText().toString()), txtNum.getText().toString());

        for (int r = 0; r < lst.size(); r++){
            TableRow tr = new TableRow(this);
            for (int c= 0 ; c < 1; c++){
                final LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout layout_sub = new LinearLayout(this);
                layout_sub.setOrientation(LinearLayout.VERTICAL);
                HorizontalScrollView layout_scroll_Ho = new HorizontalScrollView(this);

                final TextView tit = new TextView(this);
                TextView pub = new TextView(this);
                TextView cha = new TextView(this);
                ImageView im = new ImageView (this);
                Picasso.get().load(lst.get(r).getImage().toString()).into(im);
                im.setPadding(0, 0, 0, 0); //padding in each image if needed

                final int final_r = r;
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Debug.debug("CLICK", lst.get(final_r).getId());

                        try {
                            createPopUp(v, lst.get(final_r).getTitles().toString(),
                                    lst.get(final_r).getChannel().toString(),
                                    lst.get(final_r).getId().toString(),
                                    lst.get(final_r).getImage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                });

                tit.setText(lst.get(r).getTitles().toString());
                pub.setText(lst.get(r).getPublish().toString());
                cha.setText(lst.get(r).getChannel().toString());
                layout_sub.addView(tit);
                layout_sub.addView(pub);
                layout_sub.addView(cha);
                layout_scroll_Ho.addView(layout_sub);

                layout.addView(im);
                layout.addView(layout_scroll_Ho);
             //   tr.addView(im, 300,300);
                tr.addView(layout);
            }
            layoutTable.addView(tr);
        }

    }

    private List<YoutubeData> getYoutubeData (String api, String title, String num) {
        try {
            // JSONObject json = URL_Decoder.readYoutube("AIzaSyDE2igQOTyQ6XgJM03wLazUBF_zmWYWx4Q", "unravel", "1", "DETAIL");
            // Debug.debug("RESULT", json);

            Json_Decoder jd = new Json_Decoder();
            List<YoutubeData> lst = jd.getJsonContent(api, title, num, "DETAIL");
            return lst;
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createPopUp(View v, String title, String channel, String id, Object image) throws UnsupportedEncodingException {


        String url = getResources().getString(R.string.ngrok) + "/leeleelookupphp/node/index.php?t=" + URL_Encoder.get_URL_Encoder_Mod_1(title) + "&c=" + URL_Encoder.get_URL_Encoder_Mod_1(channel) + "&i=" + URL_Encoder.get_URL_Encoder_Mod_1(id) + "&im=" + image.toString();
        Debug.debug("TEST URL", url);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_convert, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        WebView wv_1 = popupView.findViewById(R.id.wvBrowser);

        wv_1.setVerticalScrollBarEnabled(false);
        wv_1.getSettings().setDomStorageEnabled(true);
        wv_1.getSettings().setSaveFormData(true);
        wv_1.getSettings().setAllowContentAccess(true);
        wv_1.getSettings().setAllowFileAccess(true);
        wv_1.getSettings().setAllowFileAccessFromFileURLs(true);
        wv_1.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv_1.getSettings().setSupportZoom(true);
        wv_1.getSettings().setJavaScriptEnabled(true);
        wv_1.getSettings().setLoadWithOverviewMode(true);
        //wv_1.getSettings().setUseWideViewPort(true);
        wv_1.getSettings().setBuiltInZoomControls (true);
        wv_1.getSettings().setDisplayZoomControls (true);
        wv_1.getSettings().setSupportZoom(true);
        wv_1.getSettings().setDefaultTextEncodingName("utf-8");
        wv_1.setWebViewClient(new WebViewClient());
        wv_1.setClickable(true);
        wv_1.setWebChromeClient(new WebChromeClient());

        wv_1.loadUrl(url);




        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    private void createPopUp_2(View v, String title, String chandler, String id, Object image) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_popup, null);

        EditText et_title = popupView.findViewById(R.id.txtPopTitle);
        EditText et_chandler = popupView.findViewById(R.id.txtPopChandle);
        EditText et_id = popupView.findViewById(R.id.txtPopId);
        ImageView img = popupView.findViewById(R.id.imgPop);
        Picasso.get().load(image.toString()).into(img);

        et_chandler.setEnabled(false);
        et_title.setEnabled(false);
        et_id.setEnabled(false);

        et_chandler.setText(chandler);
        et_title.setText(title);
        et_id.setText(id);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
