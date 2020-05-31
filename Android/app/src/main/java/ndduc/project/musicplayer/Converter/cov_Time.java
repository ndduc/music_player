package ndduc.project.musicplayer.Converter;

public class cov_Time {
    public static String getTime(long duration) {
        long millis = duration % 1000;
        long second = (duration / 1000) % 60;
        long minute = (duration / (1000 * 60)) % 60;
        long hour = (duration / (1000 * 60 * 60)) % 24;
        String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
        return time;
    }
}
