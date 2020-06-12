package ndduc.project.musicplayer.Container;

public class Directory {
    private Object title;
    public Directory(Object titles) {
        this.title = titles;
    }

    public Object getDir() {
        return title;
    }

    public void setDir(Object title) {
        this.title = title;
    }
}
