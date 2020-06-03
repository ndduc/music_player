package ndduc.project.musicplayer.Helper;

import java.util.List;

public class Debug {

    public static void debug(Object x, Object y) {
        System.out.println("[Debug]\t\t" + x + "\t" + y);
    }

    public static void debug_List(Object what, List<?> col) {
        for(int i = 0; i < col.size(); i++) {
            System.out.println("[Debug]  " + what + "\t" + i + "\t\t" + col.get(i) );
        }
    }
}
