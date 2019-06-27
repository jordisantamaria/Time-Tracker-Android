package timetracker.com.timetracker;


public class ItemList_Interval {
    private String initDate;
    private String finalDate;
    private String duration;


    public ItemList_Interval() {
        super();
    }

    public ItemList_Interval(String initD, String finalD, String duration) {
        super();
        this.initDate = initD;
        this.finalDate = finalD;
        this.duration = duration;
    }

    public void setInitDate(String date){this.initDate = date;}
    public void setFinaltDate(String date){this.finalDate = date;}
    public void setDuration(String date){this.duration = date;}
    public String getInitDate(){return initDate;}
    public String getFinalDate(){return finalDate;}
    public String getDuration(){return duration;}
}