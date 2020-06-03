package ndduc.project.musicplayer.Container;

import ndduc.project.musicplayer.Helper.Debug;

public class YoutubeData {
    private Object id;
    private Object titles;
    private Object image;
    private Object channel;
    private Object publish;
    private Object desc;

    public YoutubeData(Object id, Object titles, Object image, Object channel, Object publish, Object desc) {
        this.id = id; this.titles = titles; this.image = image; this.channel = channel; this.publish = publish; this.desc = desc;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getTitles() {
        return titles;
    }

    public void setTitles(Object titles) {
        this.titles = titles;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getChannel() {
        return channel;
    }

    public void setChannel(Object channel) {
        this.channel = channel;
    }

    public Object getPublish() {
        return publish;
    }

    public void setPublish(Object publish) {
        this.publish = publish;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }


    public void print() {
        Debug.debug("Video ID", getId());
        Debug.debug("Channel", getChannel());
        Debug.debug("Publish", getPublish());
        Debug.debug("Title", getTitles());
        Debug.debug("Desc", getDesc());
        Debug.debug("Image", getImage());
    }


    /*
    * Debug.debug("Video ID", _id.get("videoId"));
            Debug.debug("Channel", _snip.get("channelTitle"));
            Debug.debug("Publish", _snip.get("publishTime"));
            Debug.debug("Title", _snip.get("title"));
            Debug.debug("Desc", _snip.get("description"));
            Debug.debug("Image", _snipSub.get("url"));
    * */
}
