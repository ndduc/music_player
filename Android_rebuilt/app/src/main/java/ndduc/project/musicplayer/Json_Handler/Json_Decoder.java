package ndduc.project.musicplayer.Json_Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ndduc.project.musicplayer.Container.Directory;
import ndduc.project.musicplayer.Container.Titles;
import ndduc.project.musicplayer.Container.YoutubeData;
import ndduc.project.musicplayer.Helper.Debug;
import ndduc.project.musicplayer.URL_Handler.URL_Decoder;

public class Json_Decoder {
    JSONObject root;

    public Json_Decoder(){};
    public Json_Decoder(JSONObject root) {
        this.root = root;
    }

    public List<Titles> populateTiles() {
        List<Titles> lst = new ArrayList<>();
        try {
            JSONArray jar = root.getJSONArray("titles");
            for(int i = 0; i < jar.length();i++) {
                //Debug.debug(i, jar.get(i));
                Titles tit = new Titles(jar.get(i));
                lst.add(tit);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst;
    }

    public List<Directory> populateDirectory() {
        List<Directory> lst = new ArrayList<>();
        try {
            JSONArray jar = root.getJSONArray("dir");
            for(int i = 0; i < jar.length();i++) {
                //Debug.debug(i, jar.get(i));
                Directory tit = new Directory(jar.get(i));
                lst.add(tit);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst;
    }

    public List<Titles> populateTiles_2() {
        List<Titles> lst = new ArrayList<>();
        try {
            JSONObject jar = root.getJSONObject("titles");
            Debug.debug("TEST JSON", jar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst;
    }




    /**
     * Method decode json content from youtube  url
     * Static method
     * */

    public List<YoutubeData> getJsonContent (String key, String title, String result, String option) throws Exception {
        List<YoutubeData> lst = new ArrayList<>();
        JSONObject root = URL_Decoder.readYoutube(key, title, result, option);
        JSONArray jsonArray = root.getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject _sub = jsonArray.getJSONObject(i);
            JSONObject _id = _sub.getJSONObject("id");
            JSONObject _snip = _sub.getJSONObject("snippet");
            JSONObject _snipSub = _snip.getJSONObject("thumbnails").getJSONObject("medium");
            YoutubeData yd = new YoutubeData(_id.get("videoId"), _snip.get("title"), _snipSub.get("url"), _snip.get("channelTitle"),  _snip.get("publishTime"), _snip.get("description"));
            //yd.print();
            lst.add(yd);
        }
        return lst;
    }
}
