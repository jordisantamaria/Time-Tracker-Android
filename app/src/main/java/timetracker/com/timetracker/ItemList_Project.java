package timetracker.com.timetracker;


public class ItemList_Project {
    private int icon;
    private String name;
    private String duration;

    public ItemList_Project() {
        super();
    }

    public ItemList_Project(int icon, String name, String duration) {
        super();
        this.icon = icon;
        this.name = name;
        this.duration = duration;
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


}