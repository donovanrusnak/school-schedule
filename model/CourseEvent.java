package model;

public abstract class CourseEvent {
    protected String name;
    protected String date;

    public CourseEvent(String name, String date){
        this.name = name;
        this.date = date;
    }

    public abstract void printDate();

    public String getName() {return name;}
    public String getDate() {return date;}

    public void setName(String name) {this.name = name;}
    public void setDate(String date) {this.date = date;}
}