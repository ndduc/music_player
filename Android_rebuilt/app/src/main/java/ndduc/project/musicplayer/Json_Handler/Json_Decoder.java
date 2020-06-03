package ndduc.project.musicplayer.Json_Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Json_Decoder {
    JSONObject root;


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
}
