package timetracker.com.timetracker;


public class ItemList {
    private int icon;
    private String name;
    private String duration;
    private int playIcon;
    private int pauseIcon;

    public ItemList() {
        super();
    }

    public ItemList(int icon, String name, String duration) {
        super();
        this.icon = icon;
        this.name = name;
        this.duration = duration;
        this.playIcon = R.mipmap.ic_play;
        this.pauseIcon = R.mipmap.ic_pause;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String duration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getDuration() {
        return duration;
    }

    public int getplayIcon() {
        return playIcon;
    }
    public void setPlayIcon(int newIcon){this.playIcon = newIcon;}
    public int getpauseIcon() {
        return pauseIcon;
    }
    public void setPauseIcon(int newIcon){this.pauseIcon = newIcon;}
}