package timetracker.com.timetracker;


public class ItemList_menuPrincipal {
    private int icon;
    private String name;
    private int type;


    public ItemList_menuPrincipal() {
        super();
    }

    public ItemList_menuPrincipal(int icon, String name,int type) {
        super();
        this.icon = icon;
        this.name = name;
        this.type = type;

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

    public void setType (int i) {this.type = i;}

    public int getType() {return type;}


}