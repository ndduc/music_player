package ndduc.project.musicplayer.Helper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Converter {
    public static List<Object> convertList(Object[] arr) {
        List<Object> lst = Arrays.asList(arr);
        return lst;
    }
}
